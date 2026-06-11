package com.fidelite.exceptions.technical;

public class NotFoundElementException extends RuntimeException {
    public NotFoundElementException(String message) {
        super(message);
    }
}
