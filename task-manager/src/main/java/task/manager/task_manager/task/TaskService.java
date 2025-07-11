package task.manager.task_manager.task;

import java.util.List;

public interface TaskService {
    void createTask(TaskRequest taskRequest);
    TaskResponse getTask(Long id);
    List<TaskResponse> getTasks();
    void deleteTask(Long id);
    void updateTask(Long taskId, TaskUpdateRequest taskUpdateRequest);

}
