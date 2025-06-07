package task.manager.task_manager.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TaskRequest(

        @NotBlank(message = "Title is required")
        @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
        String title,

        @NotBlank(message = "Description is required")
        @Size(min = 1, max = 500, message = "Description must be between 1 and 500 characters")
        String description,

        @NotNull(message = "Deadline is required")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate deadline

) {}
