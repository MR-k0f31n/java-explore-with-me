package ru.practicum.exception;

/**
 * @author MR.k0F31n
 */
public class EmailConflictException extends RuntimeException {
    public EmailConflictException(String message) {
        super(message);
    }
}