package task.manager.task_manager.task;

import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task mapToTask(TaskRequest taskRequest) {

        return Task.builder()
                .title(taskRequest.title())
                .description(taskRequest.description())
                .deadline(taskRequest.deadline())
                .build();
    }

    public TaskResponse mapToTaskResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDeadline()
        );
    }
}
