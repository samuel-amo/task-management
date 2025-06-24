package task.manager.task_manager.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;


import java.util.List;

public record SignUpRequest(

        @NotBlank(message = "Email is required")
        @Email(message = "Email format is invalid")
        String email,


        String password,


        @NotEmpty(message = "At least one role is required")
        List<String> roles

) {}