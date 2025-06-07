package task.manager.task_manager.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import task.manager.task_manager.task.NoContent;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse<NoContent>> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        LOGGER.warn("User already exists: {}", ex.getMessage());

        return buildFailResponse(ex.getMessage(), NoContent.INSTANCE, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ApiErrorResponse<NoContent>> handleTaskNotFound(TaskNotFoundException ex) {
        LOGGER.warn("Task not found: {}", ex.getMessage());
        return buildFailResponse("Task not found.", NoContent.INSTANCE, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiErrorResponse<NoContent>> handleIllegalState(IllegalStateException ex) {
        LOGGER.warn("Illegal state: {}", ex.getMessage());
        return buildFailResponse(ex.getMessage(), NoContent.INSTANCE, HttpStatus.CONFLICT);
    }



    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse<NoContent>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        String message;
        message = "HTTP method " + ex.getMethod() + " is not supported for this endpoint.";
        LOGGER.warn(message);
        return buildFailResponse(message, NoContent.INSTANCE, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ApiErrorResponse<NoContent>> handleSecurity(SecurityException ex) {
        LOGGER.warn("Security exception: {}", ex.getMessage());
        return buildFailResponse("Access denied", NoContent.INSTANCE, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiErrorResponse<NoContent>> handleAuthorizationDenied(AuthorizationDeniedException ex) {
        LOGGER.warn("Authorization denied: {}", ex.getMessage());
        return buildFailResponse("You are not authorized to perform this action.", NoContent.INSTANCE, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse<NoContent>> handleUnexpected(Exception ex) {
        LOGGER.error("Unexpected error: {}", ex.getMessage(), ex);
        return buildErrorResponse(NoContent.INSTANCE);
    }



    private <T> ResponseEntity<ApiErrorResponse<T>> buildFailResponse(String message, T data, HttpStatus status) {
        return ResponseEntity.status(status).body(
                new ApiErrorResponse<>(
                        "fail",
                        message,
                        data
                )
        );
    }

    private <T> ResponseEntity<ApiErrorResponse<T>> buildErrorResponse(T data) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorResponse<>(
                        "error",
                        "An unexpected error occurred. Please try again later.",
                        data

                )
        );
    }
}
