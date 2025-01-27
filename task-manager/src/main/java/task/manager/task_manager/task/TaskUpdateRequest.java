package task.manager.task_manager.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TaskUpdateRequest(

        Long taskId,

        @Size(max = 100, message = "Title must be up to 100 characters")
        String title,

        @Size(max = 500, message = "Description must be up to 500 characters")
        String description,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate deadline,

        @Pattern(regexp = "LOW|MEDIUM|HIGH", message = "Priority must be one of the following values: LOW, MEDIUM, HIGH")
        String priority,

        @Pattern(regexp = "PENDING|IN_PROGRESS|COMPLETED", message = "Status must be one of the following values: PENDING, IN_PROGRESS, COMPLETED")
        String status

) {}
