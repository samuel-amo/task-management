package task.manager.task_manager.user;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task.manager.task_manager.task.ApiSuccessResponse;
import task.manager.task_manager.task.NoContent;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiSuccessResponse<NoContent>> register(@Valid @RequestBody SignUpRequest signUpRequest) {
        authService.register(signUpRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccessResponse<>(
                        "success",
                        NoContent.INSTANCE));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiSuccessResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity
                .ok(new ApiSuccessResponse<>(
                        "success",
                        authService.login(loginRequest)));
    }
}
