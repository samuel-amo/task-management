package task.manager.task_manager.task;

import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task mapToTask(TaskRequest taskRequest) {

        return Task.builder()
                .title(taskRequest.title())
                .description(taskRequest.description())
                .deadline(taskRequest.deadline())
                .status(Status.PENDING)
                .priority(Priority.valueOf(taskRequest.priority().toUpperCase()))
                .build();
    }

    public TaskResponse mapToTaskResponse(Task task) {
        return new TaskResponse(
                task.getTaskId(),
                task.getTitle(),
                task.getDescription(),
                task.getDeadline(),
                task.getPriority().toString(),
                task.getStatus().toString()
        );
    }
}
