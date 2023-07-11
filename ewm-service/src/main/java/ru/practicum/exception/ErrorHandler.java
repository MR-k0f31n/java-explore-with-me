package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author MR.k0F31n
 */
@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final NotFoundException exception) {
        log.error("Error! Not Found, server status: '{}' text message: '{}'", HttpStatus.NOT_FOUND, exception.getMessage());

        return ApiError.builder()
                .status(HttpStatus.NOT_FOUND.toString())
                .message(exception.getMessage())
                .reason("Requested data not found")
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleValidationException(final ValidationException exception) {
        log.error("Error! Validation fault, server status: '{}' text message: '{}'", HttpStatus.NOT_FOUND, exception.getMessage());

        return ApiError.builder()
                .status(HttpStatus.CONFLICT.toString())
                .message(exception.getMessage())
                .reason("The data is not correct")
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleEmailConflictException(final EmailConflictException exception) {
        log.error("Error! Email conflict, server status: '{}' text message: '{}'", HttpStatus.NOT_FOUND, exception.getMessage());

        return ApiError.builder()
                .status(HttpStatus.CONFLICT.toString())
                .message(exception.getMessage())
                .reason("Email conflict, email exist")
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleIncorrectParametersException(IncorrectParametersException exception) {
        log.error("Error! Bad request, server status: '{}' text message: '{}'", HttpStatus.BAD_REQUEST, exception.getMessage());
        return ApiError.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .message(exception.getMessage())
                .reason("Incorrect parameters")
                .build();
    }
}
