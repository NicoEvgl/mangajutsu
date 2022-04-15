package com.mangajutsu.api.exceptions;

public class ResourceAlreadyExistException extends Exception {
    public ResourceAlreadyExistException() {
        super();
    }

    public ResourceAlreadyExistException(String msg) {
        super(msg);
    }

    public ResourceAlreadyExistException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
