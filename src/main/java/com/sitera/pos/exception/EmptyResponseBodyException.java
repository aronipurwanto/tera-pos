package com.sitera.pos.exception;

public class EmptyResponseBodyException extends RuntimeException {
    public EmptyResponseBodyException(String message) {
        super(message);
    }

    public EmptyResponseBodyException(String message, Throwable cause) {
        super(message, cause);
    }
}
