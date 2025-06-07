package task.manager.task_manager.task;

import org.springframework.stereotype.Service;
import task.manager.task_manager.exception.TaskNotFoundException;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public void createTask(TaskRequest taskRequest) {

        Task task = taskMapper.mapToTask(taskRequest);
        taskRepository.save(task);
    }

    public TaskResponse getTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found or access denied"));
        return taskMapper.mapToTaskResponse(task);
    }

    public List<TaskResponse> getTasks() {

        return taskRepository.findAll()
                .stream()
                .map(taskMapper::mapToTaskResponse)
                .toList();
    }
    public void deleteTask(Long id) {

        taskRepository.deleteById(id);
    }

    public void updateTask(Long taskId, TaskUpdateRequest taskUpdateRequest) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found or access denied"));

        task.setTitle(taskUpdateRequest.title());
        task.setDescription(taskUpdateRequest.description());
        task.setDeadline(taskUpdateRequest.deadline());

        taskRepository.save(task);
    }
}
