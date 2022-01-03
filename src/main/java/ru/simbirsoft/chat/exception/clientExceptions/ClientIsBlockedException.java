package ru.simbirsoft.chat.exception.clientExceptions;

import java.sql.Timestamp;

public class ClientIsBlockedException extends RuntimeException {

    public ClientIsBlockedException(String name, Timestamp endBan) {
        super(name + " client blocked until " + endBan);
    }
}
