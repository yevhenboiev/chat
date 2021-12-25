package ru.simbirsoft.chat.exception.roomExceptions;

public class NotExistRoomException extends RuntimeException {
    public NotExistRoomException(Long id) {
        super(id + " ID room not exist");
    }
}
