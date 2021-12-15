package ru.simbirsoft.chat.exception.messageExceptions;

public class NotExistMessage extends RuntimeException {
    public NotExistMessage(Long id) {
        super(id + " ID message not found");
    }
}
