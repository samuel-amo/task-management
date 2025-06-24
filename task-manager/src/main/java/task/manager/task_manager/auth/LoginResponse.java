package task.manager.task_manager.auth;

public record LoginResponse(

        String userEmail,
        String token
){}
