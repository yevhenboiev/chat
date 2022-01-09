package ru.simbirsoft.chat.exception.clientExceptions;

public class NotAccessException extends RuntimeException {

    public NotAccessException(String name) {
        super(name + ",  you do not have access");
    }
}
