package task.manager.task_manager.auth;

public interface AuthService {

    void register(SignUpRequest signUpRequest);
    LoginResponse login(LoginRequest request);
}
