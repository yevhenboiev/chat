package ru.simbirsoft.chat.exception.clientExceptions;

public class ExistClientException extends RuntimeException{

    public ExistClientException(String name) {
        super(name + " is exist, please send another name");
    }
}
