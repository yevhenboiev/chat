package ru.simbirsoft.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import ru.simbirsoft.chat.dto.*;
import ru.simbirsoft.chat.entity.Client;
import ru.simbirsoft.chat.entity.Room;
import ru.simbirsoft.chat.entity.enums.Role;
import ru.simbirsoft.chat.exception.clientExceptions.ClientIsBlockedException;
import ru.simbirsoft.chat.exception.clientExceptions.NotAccessException;
import ru.simbirsoft.chat.exception.clientExceptions.NotExistClientException;
import ru.simbirsoft.chat.exception.roomExceptions.ExistRoomException;
import ru.simbirsoft.chat.exception.roomExceptions.NotExistRoomException;
import ru.simbirsoft.chat.helper.BotContext;
import ru.simbirsoft.chat.mapper.ClientMapper;
import ru.simbirsoft.chat.service.BotRoomService;
import ru.simbirsoft.chat.service.ClientService;
import ru.simbirsoft.chat.service.MessageService;
import ru.simbirsoft.chat.service.RoomService;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BotRoomServiceImpl implements BotRoomService {

    private final ClientService clientService;
    private final ClientMapper clientMapper;
    private final MessageService messageService;
    private final RoomService roomService;

    @Override
    public MessageDto processingRequest(User user, Room room, CreateMessageRequestDto createMessageRequestDto) {
        Client client = clientService.getByLogin(user.getUsername());
        clientService.checkBlockClient(client);
        clientService.checkClientInRoom(client, room);
        messageService.save(user, room, createMessageRequestDto);
        return createBotMessage(room.getId(), responseMessage(user, room, createMessageRequestDto.getContent()));
    }

    private String responseMessage(User user, Room room, String requestUserCommand) {
        String[] entityAndAction = requestUserCommand.split(" ", 3);
        if (entityAndAction.length >= 2) {
            String parameter = entityAndAction[0] + entityAndAction[1];
            switch (entityAndAction[0]) {
                case ("//room"):
                    return switch (entityAndAction[1]) {
                        case ("create") -> doSaveRoom(user, parameter);
                        case ("remove") -> doRemoveRoom(user, parameter);
                        case ("rename") -> doRenameRoom(user, parameter);
                        case ("connect") -> doConnectedInRoom(user, parameter);
                        case ("disconnect") -> doDisconnectedOfRoom(user, room, parameter);
                        default -> "Incorrect request, send -> //help";
                    };
                case ("//user"):
                    return switch (entityAndAction[1]) {
                        case ("rename") -> doRenamedClient(user, parameter);
                        case ("ban") -> doBlockedClient(user, parameter);
                        case ("moderator") -> doActionOnModerator(user, parameter);
                        default -> "Incorrect request, send -> //help";
                    };
            }
        }
        if (requestUserCommand.equals("//help")) {
            return BotContext.getHelp();
        }
        return "Incorrect request, send -> //help";
    }

    private String doSaveRoom(User user, String parameter) {
        try {
            CreateRoomRequestDto requestRoomDto = BotContext.createRoom(parameter);
            roomService.save(user, requestRoomDto);
            return "Room " + requestRoomDto.getRoomName() + " created";
        } catch (ExistRoomException exception) {
            return exception.getMessage();
        }
    }

    private String doRemoveRoom(User user, String parameter) {
        try {
            Room room = roomService.findRoomByName(BotContext.removeRoom(parameter));
            roomService.deleteById(user, room);
            return "Room " + room.getRoomName() + "removed";
        } catch (NotExistRoomException exception) {
            return exception.getMessage();
        }
    }

    private String doRenameRoom(User user, String parameter) {
        try {
            Room room = roomService.findRoomByName(BotContext.foundFirstParameter(parameter));
            ChangeRoomNameDto newRoomName = new ChangeRoomNameDto(BotContext.foundSecondParameter(parameter));
            roomService.renameRoom(user, room, newRoomName);
            return "Room " + room.getRoomName() + " renamed to " + newRoomName.getRoomName();
        } catch (NotExistRoomException exception) {
            return exception.getMessage();
        }
    }

    private String doConnectedInRoom(User user, String parameter) {
        try {
            Room room = roomService.findRoomByName(BotContext.connectedInRoom(parameter));
            Client client = clientService.getByLogin(user.getUsername());
            roomService.addUserInRoom(user, room, client);
            if (parameter.contains("-l")) {
                Client addingClient = clientService.getByLogin(BotContext.foundLoginClient(parameter));
                roomService.addUserInRoom(user, room, addingClient);
                return addingClient.getLogin() + " connected in " + room.getRoomName();
            }
            return "You connected in the Room";
        } catch (NotExistRoomException | ClientIsBlockedException | NotAccessException exception) {
            return exception.getMessage();
        }
    }

    private String doDisconnectedOfRoom(User user, Room room, String parameter) {
        try {
            Client disconnectedClient = clientService.getByLogin(user.getUsername());
            Room disconnectedRoom = room;
            if (!parameter.contains("{")) {
                roomService.removeUserInRoom(user, disconnectedRoom, disconnectedClient);
                return "You disconnected of Chat Bot";
            }
            String foundRoomName = BotContext.foundFirstParameter(parameter);
            disconnectedRoom = roomService.findRoomByName(foundRoomName);
            clientService.checkCreatorRoomAndRoleAdmin(disconnectedClient, disconnectedRoom);
            String foundClient = BotContext.foundLoginClient(parameter);
            disconnectedClient = clientService.getByLogin(foundClient);
            roomService.removeUserInRoom(user, disconnectedRoom, disconnectedClient);
            if (parameter.contains("-m")) {
                Long timeBanned = BotContext.foundTimeForBan(parameter);
                clientService.blockedClient(disconnectedClient, timeBanned);
                return "User " + disconnectedClient.getLogin() + " disconnected of " + disconnectedRoom.getRoomName() +
                        " and banned for " + timeBanned + " minutes";
            }
            return "User " + disconnectedClient.getLogin() + " disconnected of " + disconnectedRoom.getRoomName();
        } catch (NotExistRoomException | NotAccessException | NotExistClientException exception) {
            return exception.getMessage();
        }
    }

    private String doRenamedClient(User user, String parameter) {
        try {
            Client renamedClient = clientService.getByLogin(user.getUsername());
            ClientDto renamedClientDto = clientMapper.toDTO(renamedClient);
            String newNameClient = BotContext.foundFirstParameter(parameter);
            if (renamedClient.getRole().equals(Role.ADMIN) && parameter.contains("} || {")) {
                String loginRenamedClient = BotContext.foundFirstParameter(parameter);
                renamedClient = clientService.getByLogin(loginRenamedClient);
                renamedClientDto = clientMapper.toDTO(renamedClient);
                newNameClient = BotContext.foundSecondParameter(parameter);
            }
            renamedClientDto.setLogin(newNameClient);
            clientService.update(renamedClient.getId(), renamedClientDto);
            return renamedClient.getLogin() + " renamed to " + newNameClient;
        } catch (NotExistClientException | NotAccessException exception) {
            return exception.getMessage();
        }
    }

    private String doActionOnModerator(User user, String parameter) {
        try {
            Client admin = clientService.getByLogin(user.getUsername());
            if (!admin.getRole().equals(Role.ADMIN)) {
                return "This action only for Administration";
            }
            String clientLogin = BotContext.foundFirstParameter(parameter);
            Client expectedClient = clientService.getByLogin(clientLogin);
            ClientDto expectedClientDto = clientMapper.toDTO(expectedClient);
            if (expectedClient.getRole().equals(Role.ADMIN)) {
                return expectedClient.getLogin() + " it's Administrator";
            }
            if (parameter.contains("-n")) {
                expectedClientDto.setRole(Role.MODERATOR);
                clientService.update(expectedClient.getId(), expectedClientDto);
                return expectedClient.getLogin() + " became a Moderator";
            } else if (parameter.contains("-d")) {
                expectedClientDto.setRole(Role.USER);
                clientService.update(expectedClient.getId(), expectedClientDto);
                return expectedClient.getLogin() + " became a User";
            }
            return "Incorrect request, send -> //help";
        } catch (NotExistClientException exception) {
            return exception.getMessage();
        }
    }

    private String doBlockedClient(User user, String parameter) {
        try {
            Client adminOrModerator = clientService.getByLogin(user.getUsername());
            if (adminOrModerator.getRole().equals(Role.USER)) {
                return "This action only for Moderator or Administration";
            }
            String loginBannedClient = BotContext.foundLoginClient(parameter);
            Client bannedClient = clientService.getByLogin(loginBannedClient);
            Long timeBanned = BotContext.foundTimeForBan(parameter);
            clientService.blockedClient(bannedClient, timeBanned);
            return "User " + bannedClient.getLogin() + " blocked for " + timeBanned + " minutes";
        } catch (NotExistClientException exception) {
            return exception.getMessage();
        }
    }

    private MessageDto createBotMessage(Long roomId, String content) {
        MessageDto botMessage = new MessageDto();
        botMessage.setId(0L);
        botMessage.setClientId(1L);
        botMessage.setRoomId(roomId);
        botMessage.setTime(Timestamp.valueOf(LocalDateTime.now()));
        botMessage.setContent(content);
        return botMessage;
    }

}
