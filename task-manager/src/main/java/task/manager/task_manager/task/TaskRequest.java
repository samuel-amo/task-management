package task.manager.task_manager.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent; // 1. Import for date validation
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;


public record TaskRequest(
        @NotBlank(message = "Title is required")
        @Size(max = 100, message = "Title must not exceed 100 characters")
        String title,

        @NotBlank(message = "Description is required")
        @Size(max = 500, message = "Description must not exceed 500 characters")
        String description,

        @NotNull(message = "Deadline is required")
        @FutureOrPresent(message = "Deadline must be today or in the future") // 2. Added date validation
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate deadline
) {}