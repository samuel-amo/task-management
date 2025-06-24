package task.manager.task_manager.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;


public record TaskUpdateRequest(


        @Size(max = 100, message = "Title must not exceed 100 characters")
        String title,

        @Size(max = 500, message = "Description must not exceed 500 characters")
        String description,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate deadline

) {}