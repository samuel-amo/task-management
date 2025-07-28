// File: src/main/java/task/manager/task_manager/auth/AuthController.java
package task.manager.task_manager.auth;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import task.manager.task_manager.common.responses.JSendSuccessResponse;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<JSendSuccessResponse<Void>> register(@Valid @RequestBody SignUpRequest signUpRequest) {
        authService.register(signUpRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(JSendSuccessResponse.empty());
    }


    @PostMapping("/login")
    public ResponseEntity<JSendSuccessResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginData = authService.login(loginRequest);

        return ResponseEntity.ok(JSendSuccessResponse.of(loginData));
    }
}
