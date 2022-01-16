package ru.simbirsoft.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import ru.simbirsoft.chat.dto.ChangeRoomNameDto;
import ru.simbirsoft.chat.dto.ClientDto;
import ru.simbirsoft.chat.dto.CreateMessageRequestDto;
import ru.simbirsoft.chat.dto.MessageDto;
import ru.simbirsoft.chat.entity.Client;
import ru.simbirsoft.chat.entity.Room;
import ru.simbirsoft.chat.entity.enums.Role;
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
                    switch (entityAndAction[1]) {
                        case ("create"):
                            roomService.save(user, BotContext.createRoom(requestUserCommand));
                            return "Room created";
                        case ("remove"):
                            Room removedRoom = roomService.findRoomByName(BotContext.removeRoom(requestUserCommand));
                            roomService.deleteById(user, removedRoom);
                            return "Room remove";
                        case ("rename"):
                            String findRenameRoomInCommand = requestUserCommand.replaceAll("//room rename", "");
                            Room renamedRoom = roomService.findRoomByName(BotContext.foundFirstParameter(findRenameRoomInCommand));
                            ChangeRoomNameDto newRoomName = new ChangeRoomNameDto(BotContext.foundSecondParameter(findRenameRoomInCommand));
                            roomService.renameRoom(user, renamedRoom, newRoomName);
                            return "Room rename";
                        case ("connect"):
                            Room connectedRoom = roomService.findRoomByName(BotContext.connectedInRoom(requestUserCommand));
                            Client client = clientService.getByLogin(user.getUsername());
                            roomService.addUserInRoom(user, connectedRoom, client);
                            if (requestUserCommand.contains("-l")) {
                                String connectedLogin = requestUserCommand.replaceAll("//room connect", "");
                                Client addingClient = clientService.getByLogin(BotContext.foundLoginClient(connectedLogin));
                                roomService.addUserInRoom(user, connectedRoom, addingClient);
                            }
                            return "Connected in the Room";
                        case ("disconnect"):
                            Room disconnectedRoom = room;
                            Client disconnectedClient = clientService.getByLogin(user.getUsername());
                            if (parameter.contains("-l")) {
                                disconnectedRoom = roomService.findRoomByName(BotContext.foundFirstParameter(parameter));
                                disconnectedClient = clientService.getByLogin(BotContext.foundLoginClient(parameter));
                                roomService.removeUserInRoom(user, disconnectedRoom, disconnectedClient);
                                if (parameter.contains("-m")) {
                                    Long timeInMinutes = BotContext.foundTimeForBan(parameter);
                                    clientService.blockedClient(disconnectedClient, timeInMinutes);
                                    return disconnectedClient.getName() + " disconnect the room " +
                                            disconnectedRoom.getRoomName() + " and block for " + timeInMinutes + "min";
                                }
                                return disconnectedClient.getName() + " disconnect the room " +
                                        disconnectedRoom.getRoomName();
                            } else {
                                roomService.removeUserInRoom(user, disconnectedRoom, disconnectedClient);
                            }
                            return "Out of the Room";
                        default:
                            return "Incorrect request, send -> //help";
                    }
                case ("//user"):
                    switch (entityAndAction[1]) {
                        case ("rename"):
                            Client renamedClient;
                            ClientDto clientDto;
                            String firstParameter;
                            String secondParameter;
                            if(requestUserCommand.contains(",") && user.getAuthorities().equals("ADMIN")) {
                                firstParameter = BotContext.foundFirstParameter(parameter);
                                secondParameter = BotContext.foundSecondParameter(parameter);
                                renamedClient = clientService.getByLogin(firstParameter);
                                clientDto = clientMapper.toDTO(renamedClient);
                                clientDto.setName(secondParameter);
                                clientService.update(renamedClient.getId(), clientDto);
                                return "У пользователя " + renamedClient.getName() + " изменено имя";
                            }
                            renamedClient = clientService.getByLogin(user.getUsername());
                            firstParameter = BotContext.foundFirstParameter(parameter);
                            clientDto = clientMapper.toDTO(renamedClient);
                            clientDto.setName(firstParameter);
                            clientService.update(renamedClient.getId(), clientDto);
                            return "You name renamed";
                        case ("moderator"):
                            Client admin = clientService.getByLogin(user.getUsername());
                            String clientLogin = BotContext.foundFirstParameter(parameter);
                            Client expectedClient = clientService.getByLogin(clientLogin);
                            if(admin.getRole().equals(Role.ADMIN)) {
                                ClientDto clientModeratorDTO = clientMapper.toDTO(expectedClient);
                                if(parameter.contains("-n")){
                                    clientModeratorDTO.setRole(Role.MODERATOR);
                                } else if (parameter.contains("-d")) {
                                    clientModeratorDTO.setRole(Role.USER);
                                }
                                clientService.update(expectedClient.getId(), clientModeratorDTO);
                                return expectedClient.getName() + " updated";
                            }
                            return "Only for Administration";
                        case ("ban"):
                            String loginBannedClient = BotContext.foundLoginClient(parameter);
                            Client bannedClient = clientService.getByLogin(loginBannedClient);
                            Long timeBanned = BotContext.foundTimeForBan(parameter);
                            clientService.blockedClient(bannedClient, timeBanned);
                            return "User is blocked";
                        default:
                            return "Incorrect request, send -> //help";
                    }
            }
        }
        if (requestUserCommand.equals("//help")) {
            return BotContext.getHelp();
        }
        return "Incorrect request, send -> //help";
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
