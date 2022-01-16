package ru.simbirsoft.chat.exception.roomExceptions;

public class ExistRoomException extends RuntimeException {
    public ExistRoomException(String roomName) {
        super("Exist room " + roomName + " send another room name");
    }
}
