package ru.simbirsoft.chat.exception.messageExceptions;

public class NotExistMessageException extends RuntimeException {
    public NotExistMessageException() {
        super("Message not found");
    }

    public NotExistMessageException(Long id) {
        super(id + " ID message not found");
    }
}
