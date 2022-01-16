package ru.simbirsoft.chat.helper;


import ru.simbirsoft.chat.dto.CreateRoomRequestDto;

import javax.validation.ValidationException;

public class BotContext {

    public static String getHelp() {
        return """
                Все доступные команды: Комнаты:
                //room create {Название комнаты} - создает комнаты;
                \t-c закрытая комната.
                //room remove {Название комнаты} - удаляет комнату;
                //room rename {Название комнаты}, {Новое название комнаты} - переименование комнаты
                //room connect {Название комнаты} - войти в комнату;
                \t-l {login пользователя} - добавить пользователя в комнату
                //room disconnect - выйти из текущей комнаты;
                //room disconnect {Название комнаты} - выйти из заданной комнаты;
                \t-l {login пользователя} - выгоняет пользователя из комнаты.
                \t-m {Количество минут} - время на которое пользователь не сможет войти
                Пользователи:
                //user rename {Имя пользователя}
                Администратор: {login пользователя} || {Новое имя пользователя};
                //user ban;
                \t-l {login пользователя} - выгоняет пользователя из всех комнат
                \t-m {Количество минут} - время на которое пользователь не сможет войти.
                //user moderator {login пользователя} - действия над модераторами.
                \t-n - назначить пользователя модератором.
                \t-d - “разжаловать” пользователя.
                Другие:
                //help - выводит список доступных коман""";
    }

    public static CreateRoomRequestDto createRoom(String requestUserCommand) {
        checkRequest(requestUserCommand);
        int startIndex = requestUserCommand.indexOf("{");
        int endIndex = requestUserCommand.indexOf("}");
        String roomName = requestUserCommand.substring(startIndex + 1, endIndex);
        if(roomName.length() < 3 && roomName.length() < 55) {
            throw new ValidationException("Size 3 - 55 characters");
        }
        int index = requestUserCommand.indexOf("-c");
        boolean isPrivate = index != -1;
        return new CreateRoomRequestDto(roomName, isPrivate);
    }

    public static String removeRoom(String requestUserCommand) {
        if (requestUserCommand.isEmpty() || !requestUserCommand.contains("{") || requestUserCommand.contains("}") ){
            throw new ValidationException("Incorrect request");
        }
        int startIndex = requestUserCommand.indexOf("{");
        int endIndex = requestUserCommand.indexOf("}");
        return requestUserCommand.substring(startIndex + 1, endIndex);
    }

    public static String foundFirstParameter(String requestUserCommand) {
        checkRequest(requestUserCommand);
        int startIndex = requestUserCommand.indexOf("{");
        int endIndex = requestUserCommand.indexOf("}");
        return requestUserCommand.substring(startIndex + 1, endIndex);
    }

    public static String foundSecondParameter(String requestUserCommand) {
        checkRequest(requestUserCommand);
        int startIndex = requestUserCommand.lastIndexOf("{");
        int endIndex = requestUserCommand.lastIndexOf("}");

        return requestUserCommand.substring(startIndex + 1, endIndex);
    }

    public static String foundLoginClient(String requestUserCommand) {
        checkRequest(requestUserCommand);
        if(!requestUserCommand.contains("-l")) {
            throw new ValidationException("Incorrect request, send -> //help");
        }
        int index = requestUserCommand.indexOf("-l");
        String clientName = requestUserCommand.substring(index + 1);
        int startIndex = clientName.indexOf("{");
        int endIndex = clientName.indexOf("}");
        return clientName.substring(startIndex + 1, endIndex);
    }

    public static Long foundTimeForBan(String requestUserCommand) {
        checkRequest(requestUserCommand);
        if(!requestUserCommand.contains("-m")) {
            throw new ValidationException("Incorrect request, send -> //help");
        }
        int index = requestUserCommand.indexOf("-m");
        String expectedLine = requestUserCommand.substring(index + 1);
        int startIndex = expectedLine.indexOf("{");
        int endIndex = expectedLine.indexOf("}");
        String timeInMinutes = expectedLine.substring(startIndex + 1, endIndex);
        return Long.valueOf(timeInMinutes);
    }

    public static void checkRequest(String requestUserCommand) {
        if (!requestUserCommand.contains("{") && !requestUserCommand.contains("}")){
            throw new ValidationException("Incorrect request, send -> //help");
        }
    }

}
