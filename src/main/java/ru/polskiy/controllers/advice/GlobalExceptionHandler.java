package ru.polskiy.controllers.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.polskiy.exception.*;

/**
 * Global exception handler for handling specific exceptions across the application.
 * Uses {@link org.springframework.web.bind.annotation.ExceptionHandler} to handle
 * various exceptions and return appropriate error responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link AuthorizeException} and returns HTTP UNAUTHORIZED with error message.
     *
     * @param exception the AuthorizeException instance
     * @return ResponseEntity with ApplicationError and HTTP status UNAUTHORIZED
     */
    @ExceptionHandler(AuthorizeException.class)
    ResponseEntity<ApplicationError> handleAuthorizeException(AuthorizeException exception) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    /**
     * Handles {@link DuplicateRecordException} and returns HTTP BAD REQUEST with error message.
     *
     * @param exception the DuplicateRecordException instance
     * @return ResponseEntity with ApplicationError and HTTP status BAD REQUEST
     */
    @ExceptionHandler(DuplicateRecordException.class)
    ResponseEntity<ApplicationError> handleDuplicateRecordException(DuplicateRecordException exception) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    /**
     * Handles {@link InvalidCredentialsException} and returns HTTP BAD REQUEST with error message.
     *
     * @param exception the InvalidCredentialsException instance
     * @return ResponseEntity with ApplicationError and HTTP status BAD REQUEST
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    ResponseEntity<ApplicationError> handleInvalidCredentialsException(InvalidCredentialsException exception) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    /**
     * Handles {@link NotValidArgumentException} and returns HTTP BAD REQUEST with error message.
     *
     * @param exception the NotValidArgumentException instance
     * @return ResponseEntity with ApplicationError and HTTP status BAD REQUEST
     */
    @ExceptionHandler(NotValidArgumentException.class)
    ResponseEntity<ApplicationError> handleNotValidArgumentException(NotValidArgumentException exception) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    /**
     * Handles {@link RegisterException} and returns HTTP UNAUTHORIZED with error message.
     *
     * @param exception the RegisterException instance
     * @return ResponseEntity with ApplicationError and HTTP status UNAUTHORIZED
     */
    @ExceptionHandler(RegisterException.class)
    ResponseEntity<ApplicationError> handleRegisterException(RegisterException exception) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    /**
     * Handles {@link ValidationParametersException} and returns HTTP BAD REQUEST with error message.
     *
     * @param exception the ValidationParametersException instance
     * @return ResponseEntity with ApplicationError and HTTP status BAD REQUEST
     */
    @ExceptionHandler(ValidationParametersException.class)
    ResponseEntity<ApplicationError> handleGradeNotFoundException(ValidationParametersException exception) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    /**
     * Handles {@link UserNotFoundException} and returns HTTP NOT FOUND with error message.
     *
     * @param exception the UserNotFoundException instance
     * @return ResponseEntity with ApplicationError and HTTP status NOT FOUND
     */
    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<ApplicationError> handleUserNotFoundException(UserNotFoundException exception) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    /**
     * Handles {@link NoSuchWorkspaceException} and returns HTTP NOT FOUND with error message.
     *
     * @param exception the NoSuchWorkspaceException instance
     * @return ResponseEntity with ApplicationError and HTTP status NOT FOUND
     */
    @ExceptionHandler(NoSuchWorkspaceException.class)
    ResponseEntity<ApplicationError> handleNoWorkspaceException(NoSuchWorkspaceException exception){
        return buildErrorResponse(HttpStatus.NOT_FOUND,exception.getMessage());
    }

    /**
     * Builds ResponseEntity with ApplicationError containing the specified HTTP status and message.
     *
     * @param status HTTP status code
     * @param message error message
     * @return ResponseEntity with ApplicationError
     */
    private ResponseEntity<ApplicationError> buildErrorResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(ApplicationError.builder()
                .status(status.value())
                .message(message)
                .build());
    }
}