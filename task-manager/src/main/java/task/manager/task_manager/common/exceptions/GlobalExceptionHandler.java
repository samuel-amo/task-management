// File: src/main/java/task/manager/task_manager/common/exceptions/GlobalExceptionHandler.java
package task.manager.task_manager.common.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import task.manager.task_manager.auth.user.UserAlreadyExistsException;
import task.manager.task_manager.common.responses.JSendErrorResponse;
import task.manager.task_manager.common.responses.JSendFailResponse;
import task.manager.task_manager.task.TaskNotFoundException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<JSendFailResponse> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        LOGGER.warn("User already exists: {}", ex.getMessage());
        JSendFailResponse failResponse = new JSendFailResponse(Map.of("email", ex.getMessage()));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(failResponse);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<JSendFailResponse> handleTaskNotFound(TaskNotFoundException ex) {
        LOGGER.warn("Task not found: {}", ex.getMessage());
        JSendFailResponse failResponse = new JSendFailResponse(Map.of("task", "Task not found."));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(failResponse);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<JSendFailResponse> handleIllegalState(IllegalStateException ex) {
        LOGGER.warn("Illegal state: {}", ex.getMessage());
        JSendFailResponse failResponse = new JSendFailResponse(Map.of("state", ex.getMessage()));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(failResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<JSendFailResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        String message = "HTTP method '" + ex.getMethod() + "' is not supported for this endpoint.";
        LOGGER.warn(message);
        JSendFailResponse failResponse = new JSendFailResponse(Map.of("method", message));
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(failResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<JSendFailResponse> handleBadCredentials(BadCredentialsException ex) {
        LOGGER.warn("Authentication failed: Invalid credentials provided.");
        JSendFailResponse failResponse = new JSendFailResponse(Map.of("credentials", "Invalid username or password."));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(failResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JSendErrorResponse> handleUnexpected(Exception ex) {
        LOGGER.error("Unexpected error: {}", ex.getMessage(), ex);
        JSendErrorResponse errorResponse = new JSendErrorResponse("An unexpected error occurred. Please try again later.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
