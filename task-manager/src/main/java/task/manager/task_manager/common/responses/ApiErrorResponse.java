package task.manager.task_manager.common.responses;

public record ApiErrorResponse<T>(
        String status,
        String message,
        T data
) {}
