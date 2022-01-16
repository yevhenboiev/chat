package ru.simbirsoft.chat.exception.roomExceptions;

public class NotExistRoomException extends RuntimeException {

    public NotExistRoomException() {
        super("Not exist room");
    }

    public NotExistRoomException(Long id) {
        super(id + " ID room not exist");
    }

    public NotExistRoomException(String roomName) {
        super(roomName + " room not exist");
    }
}
