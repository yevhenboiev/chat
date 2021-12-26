package ru.simbirsoft.chat.exception.clientExceptions;

public class NotExistClientException extends RuntimeException {

    public NotExistClientException() {
        super("Not found client");
    }

    public NotExistClientException(Object searchKey) {
        super("Not found Client: " + searchKey);
    }


}
