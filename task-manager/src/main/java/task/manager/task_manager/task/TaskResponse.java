package task.manager.task_manager.task;

import java.time.LocalDate;

public record TaskResponse(

        Long taskId,
        String title,
        String description,
        LocalDate deadline,
        String priority,
        String status

){}