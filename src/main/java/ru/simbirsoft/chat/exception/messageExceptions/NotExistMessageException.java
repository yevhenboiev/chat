package ru.simbirsoft.chat.exception.messageExceptions;

public class NotExistMessageException extends RuntimeException {
    public NotExistMessageException(Long id) {
        super(id + " ID message not found");
    }
}
