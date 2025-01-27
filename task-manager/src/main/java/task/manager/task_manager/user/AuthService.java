package task.manager.task_manager.user;

public interface AuthService {

    void register(SignUpRequest signUpRequest);
    LoginResponse login(LoginRequest request);
}
