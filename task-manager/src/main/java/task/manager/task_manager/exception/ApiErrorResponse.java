package task.manager.task_manager.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ApiErrorResponse<T>(
        String status,
        String message,
        T data
) {}
