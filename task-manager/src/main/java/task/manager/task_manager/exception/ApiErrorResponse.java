package task.manager.task_manager.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ApiErrorResponse(
        String timestamp,
        int status,
        String error,
        String message,
        String path
) {
    public ApiErrorResponse(int status, String error, String message, String path) {
        this(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm")), status, error, message, path);
    }
}
