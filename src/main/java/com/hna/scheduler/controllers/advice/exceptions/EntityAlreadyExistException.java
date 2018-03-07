package com.hna.scheduler.controllers.advice.exceptions;

public class EntityAlreadyExistException extends RuntimeException {
    public EntityAlreadyExistException() {
        super();
    }

    public EntityAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityAlreadyExistException(String message) {
        super(message);
    }

    public EntityAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
