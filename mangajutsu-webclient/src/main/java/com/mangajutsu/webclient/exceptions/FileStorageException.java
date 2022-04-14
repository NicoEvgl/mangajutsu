package com.mangajutsu.webclient.exceptions;

public class FileStorageException extends Exception {
    public FileStorageException() {
        super();
    }

    public FileStorageException(String msg) {
        super(msg);
    }

    public FileStorageException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
