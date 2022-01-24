package ru.simbirsoft.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.chat.dto.*;
import ru.simbirsoft.chat.entity.Client;
import ru.simbirsoft.chat.entity.Room;
import ru.simbirsoft.chat.entity.Videos;
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
import ru.simbirsoft.chat.utills.SearchVideoYoutube;

import javax.validation.ValidationException;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BotRoomServiceImpl implements BotRoomService {

    private final ClientService clientService;
    private final ClientMapper clientMapper;
    private final MessageService messageService;
    private final RoomService roomService;

    @Transactional
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
            String parameter = requestUserCommand.replaceAll(entityAndAction[0] + " " + entityAndAction[1], "");
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
                case ("//yBot"):
                    return switch (entityAndAction[1]) {
                        case ("find") -> doFindVideo(parameter);
                        case ("channelInfo") -> doFindLastVideo(parameter);
                        case ("videoCommentRandom") -> doFindRandomComments(parameter);
                        case ("help") -> BotContext.getYbotHelp();
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
        } catch (ExistRoomException | ValidationException exception) {
            return exception.getMessage();
        }
    }

    private String doRemoveRoom(User user, String parameter) {
        try {
            Room room = roomService.findRoomByName(BotContext.removeRoom(parameter));
            roomService.deleteById(user, room);
            return "Room " + room.getRoomName() + " removed";
        } catch (NotExistRoomException | NotAccessException | ValidationException exception) {
            return exception.getMessage();
        }
    }

    private String doRenameRoom(User user, String parameter) {
        try {
            Room room = roomService.findRoomByName(BotContext.foundFirstParameter(parameter));
            ChangeRoomNameDto newRoomName = new ChangeRoomNameDto(BotContext.foundSecondParameter(parameter));
            roomService.renameRoom(user, room, newRoomName);
            return "Room " + room.getRoomName() + " renamed to " + newRoomName.getRoomName();
        } catch (NotExistRoomException | NotAccessException | ValidationException exception) {
            return exception.getMessage();
        }
    }

    private String doConnectedInRoom(User user, String parameter) {
        try {
            Room room = roomService.findRoomByName(BotContext.foundFirstParameter(parameter));
            Client client = clientService.getByLogin(user.getUsername());
            if (parameter.contains("-l")) {
                Client addingClient = clientService.getByLogin(BotContext.foundLoginClient(parameter));
                roomService.addUserInRoom(user, room, addingClient);
                return addingClient.getLogin() + " connected in " + room.getRoomName();
            }
            roomService.addUserInRoom(user, room, client);
            return "You connected in the Room";
        } catch (NotExistRoomException | ClientIsBlockedException | ExistRoomException
                | NotAccessException | ValidationException exception) {
            return exception.getMessage();
        }
    }

    private String doDisconnectedOfRoom(User user, Room room, String parameter) {
        try {
            Client disconnectedClient = clientService.getByLogin(user.getUsername());
            if (!parameter.contains("{")) {
                roomService.removeUserInRoom(user, room, disconnectedClient);
                return "You disconnected of Chat Bot";
            }
            Client creatorOrAdmin = clientService.getByLogin(user.getUsername());
            String foundRoomName = BotContext.foundFirstParameter(parameter);
            Room disconnectedRoom = roomService.findRoomByName(foundRoomName);
            if (!parameter.contains("-l")) {
                roomService.removeUserInRoom(user, disconnectedRoom, disconnectedClient);
                return "You disconnected of " + disconnectedRoom.getRoomName();
            }
            clientService.checkCreatorRoomAndRoleAdmin(creatorOrAdmin, disconnectedRoom);
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
        } catch (NotExistRoomException | NotAccessException | NotExistClientException | ValidationException exception) {
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
            renamedClientDto.setName(newNameClient);
            clientService.update(renamedClient.getId(), renamedClientDto);

            return renamedClient.getLogin() + " renamed to " + newNameClient;
        } catch (NotExistClientException | NotAccessException | ValidationException exception) {
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
        } catch (NotExistClientException | ValidationException exception) {
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
        } catch (NotExistClientException | ValidationException exception) {
            return exception.getMessage();
        }
    }

    private String doFindVideo(String parameter) {
        try {
            SearchVideoYoutube searchVideoYoutube = new SearchVideoYoutube("AIzaSyDRiZzpP6qt-nTdL6IDgpYJ-VUfQWpU-DI");
            String nameChannel = BotContext.foundFirstParameter(parameter);
            String channelId = searchVideoYoutube.getChannelId(nameChannel);
            String nameVideo = BotContext.foundSecondParameter(parameter);

            List<Videos> videos = searchVideoYoutube.getVideoList(nameVideo, channelId, 1L, false);
            if (videos.size() == 0) {
                return "Not found movie - '" + nameVideo + "'";
            }
            StringBuilder sb = new StringBuilder();
            for (Videos video : videos) {
                sb.append("https://www.youtube.com/watch?v=")
                        .append(video.getId())
                        .append(" | ")
                        .append(video.getTitle());
                if (parameter.contains("-k")) {
                    sb.append(" | view: ").append(video.getViewCount());
                }
                if (parameter.contains("-l")) {
                    sb.append(" | likes: ").append(video.getLikeCount());
                }
            }
            return sb.toString();
        } catch (IOException exception) {
            return "Error find movie. Use the command -> //help";
        }
    }

    private String doFindLastVideo(String parameter) {
        try {
            SearchVideoYoutube searchVideoYoutube = new SearchVideoYoutube("AIzaSyDRiZzpP6qt-nTdL6IDgpYJ-VUfQWpU-DI");
            String nameChannel = BotContext.foundFirstParameter(parameter);
            String channelId = searchVideoYoutube.getChannelId(nameChannel);
            List<Videos> videos = searchVideoYoutube.getVideoList("", channelId, 5L, true);
            if (videos.size() == 0) {
                return "Not found movie";
            }
            StringBuilder sb = new StringBuilder().append(nameChannel);
            for (Videos video : videos) {
                sb.append(" | ")
                        .append("https://www.youtube.com/watch?v=")
                        .append(video.getId())
                        .append(" | ");
            }
            return sb.toString();
        } catch (IOException exception) {
            return "Error find movie. Use the command -> //help";
        }
    }

    private String doFindRandomComments(String parameter) {
        try {
            SearchVideoYoutube searchVideoYoutube = new SearchVideoYoutube("AIzaSyDRiZzpP6qt-nTdL6IDgpYJ-VUfQWpU-DI");
            String nameChannel = BotContext.foundFirstParameter(parameter);
            String channelId = searchVideoYoutube.getChannelId(nameChannel);
            String nameVideo = BotContext.foundSecondParameter(parameter);

            List<Videos> videos = searchVideoYoutube.getVideoList(nameVideo, channelId, 1L, false);
            if (videos.size() == 0) {
                return "Not found movie - '" + nameVideo + "'";
            }

            if (videos.get(0).getCommentCount() == 0) {
                return "Can't get video comments.";
            }

            long resultCount = 1L;
            if (videos.get(0).getCommentCount() > 15) {
                resultCount = 25L;
            }

            Map<String, String> commentMap = searchVideoYoutube.getComment(videos.get(0), resultCount);
            Random random = new Random();
            int randomComment = random.nextInt(commentMap.size());
            String author = (new ArrayList<>(commentMap.keySet()).get(randomComment - 1));
            String comment = commentMap.get(author);
            return "Author: " +
                    author +
                    " " +
                    "Comment: " +
                    comment;

        } catch (IOException exception) {
            return "Error find movie. Use the command -> //help";
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
