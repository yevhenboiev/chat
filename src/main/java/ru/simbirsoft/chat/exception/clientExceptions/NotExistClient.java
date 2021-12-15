package ru.simbirsoft.chat.exception.clientExceptions;

public class NotExistClient extends RuntimeException {

    public NotExistClient(Long id) {
        super("Not found Client with id: " + id);
    }

}
