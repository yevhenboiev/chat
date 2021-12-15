package ru.simbirsoft.chat.exception.clientExceptions;

public class ExistClient extends RuntimeException{

    public ExistClient(String name) {
        super(name + " is exist, please send another name");
    }
}
