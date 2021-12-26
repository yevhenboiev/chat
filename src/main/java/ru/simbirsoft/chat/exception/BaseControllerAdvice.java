package ru.simbirsoft.chat.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.simbirsoft.chat.exception.clientExceptions.ExistClientException;
import ru.simbirsoft.chat.exception.clientExceptions.NotExistClientException;
import ru.simbirsoft.chat.exception.messageExceptions.NotExistMessageException;
import ru.simbirsoft.chat.exception.roomExceptions.NotExistRoomException;
import ru.simbirsoft.chat.exception.security.InvalidPasswordOrLoginException;
import ru.simbirsoft.chat.exception.security.JwtAuthenticationException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class BaseControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleValidationExceptions(MethodArgumentNotValidException exception) {
        return response(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler(NotExistClientException.class)
    public Object notExistClientException(NotExistClientException exception) {
        return response(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(ExistClientException.class)
    public Object notExistClientException(ExistClientException exception) {
        return response(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler(NotExistMessageException.class)
    public Object notExistClientException(NotExistMessageException exception) {
        return response(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(NotExistRoomException.class)
    public Object notExistClientException(NotExistRoomException exception) {
        return response(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public Object notExistClientException(JwtAuthenticationException exception) {
        return response(HttpStatus.UNAUTHORIZED, exception);
    }

    @ExceptionHandler(InvalidPasswordOrLoginException.class)
    public Object notExistClientException(InvalidPasswordOrLoginException exception) {
        return response(HttpStatus.FORBIDDEN, exception);
    }

    private Object response(HttpStatus httpStatus, Exception exception) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> body = new HashMap<>();
        body.put("message", exception.getMessage());
        body.put("status", httpStatus.toString());
        body.put("timestamp", LocalDateTime.now().toString());
        return new ResponseEntity<>(body, httpHeaders, httpStatus);
    }

}
