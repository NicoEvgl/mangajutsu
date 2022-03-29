package com.mangajutsu.api.exceptions;

public class UnknownIdentifierException extends Exception {
    public UnknownIdentifierException() {
        super();
    }

    public UnknownIdentifierException(String msg) {
        super(msg);
    }

    public UnknownIdentifierException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
