package task.manager.task_manager.task;

import java.time.LocalDate;

public record TaskResponse(

        Long id,
        String title,
        String description,
        LocalDate deadline

){}