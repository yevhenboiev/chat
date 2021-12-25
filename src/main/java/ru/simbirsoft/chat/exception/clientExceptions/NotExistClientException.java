package ru.simbirsoft.chat.exception.clientExceptions;

public class NotExistClientException extends RuntimeException {

    public NotExistClientException(Long id) {
        super("Not found Client with id: " + id);
    }

    public NotExistClientException(String login) {
        super("Not found Client with Login: " + login);
    }

}
