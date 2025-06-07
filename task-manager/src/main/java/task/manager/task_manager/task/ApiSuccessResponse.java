package task.manager.task_manager.task;

import java.time.Instant;
import java.util.Optional;

public record ApiSuccessResponse<T>(

        String status,
        T data


) {}

