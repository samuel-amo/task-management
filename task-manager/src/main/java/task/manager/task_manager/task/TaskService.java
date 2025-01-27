package task.manager.task_manager.task;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    void createTask(TaskRequest taskRequest);
    TaskResponse getTask(Long id);
    List<TaskResponse> getTasks();
    void deleteTask(Long id);
    void updateTask(Long taskId, TaskUpdateRequest taskUpdateRequest);
    List<TaskResponse> getFilteredTasks(Status status, Priority priority, LocalDate deadline);
}
