package ru.simbirsoft.chat.exception;

public class NotFoundClient extends RuntimeException {

    public NotFoundClient(String message) {
        super(message);
    }
}
