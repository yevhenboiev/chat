package ru.simbirsoft.chat.exception.roomExceptions;

public class NotExistRoom extends RuntimeException {
    public NotExistRoom(Long id) {
        super(id + " ID room not exist");
    }
}
