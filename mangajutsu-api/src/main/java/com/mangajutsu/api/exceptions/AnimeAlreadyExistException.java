package com.mangajutsu.api.exceptions;

public class AnimeAlreadyExistException extends Exception {
    public AnimeAlreadyExistException() {
        super();
    }

    public AnimeAlreadyExistException(String msg) {
        super(msg);
    }

    public AnimeAlreadyExistException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
