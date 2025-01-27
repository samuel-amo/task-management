package task.manager.task_manager.user;

import lombok.Builder;

@Builder
public record LoginResponse(

        String userEmail,
        String token
){}
