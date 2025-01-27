package task.manager.task_manager.task;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import task.manager.task_manager.exception.TaskNotFoundException;
import task.manager.task_manager.security.AppUserDetails;
import task.manager.task_manager.user.AppUser;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }


    private AppUser getAuthenticatedUser() {
        AppUserDetails userDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.user();
    }

    public void createTask(TaskRequest taskRequest) {
        AppUser user = getAuthenticatedUser();
        Task task = taskMapper.mapToTask(taskRequest);
        task.setAppUser(user);
        taskRepository.save(task);
    }

    public List<TaskResponse> getFilteredTasks(Status status, Priority priority, LocalDate deadline) {
        AppUser user = getAuthenticatedUser();
        return taskRepository.findTasksByUserAndCriteria(user.getEmail(), status, priority, deadline)
                .stream()
                .map(taskMapper::mapToTaskResponse)
                .collect(Collectors.toList());
    }

    public TaskResponse getTask(Long id) {
        AppUser user = getAuthenticatedUser();
        Task task = taskRepository.findByTaskIdAndAppUser(id, user)
                .orElseThrow(() -> new TaskNotFoundException("Task not found or access denied"));
        return taskMapper.mapToTaskResponse(task);
    }

    public List<TaskResponse> getTasks() {
        AppUser user = getAuthenticatedUser();
        return taskRepository.findByAppUser(user)
                .stream()
                .map(taskMapper::mapToTaskResponse)
                .collect(Collectors.toList());
    }

    public void deleteTask(Long id) {
        AppUser user = getAuthenticatedUser();
        Task task = taskRepository.findByTaskIdAndAppUser(id, user)
                .orElseThrow(() -> new TaskNotFoundException("Task not found or access denied"));
        taskRepository.delete(task);
    }

    public void updateTask(Long taskId, TaskUpdateRequest taskUpdateRequest) {
        AppUser user = getAuthenticatedUser();
        Task task = taskRepository.findByTaskIdAndAppUser(taskId, user)
                .orElseThrow(() -> new TaskNotFoundException("Task not found or access denied"));

        task.setTitle(taskUpdateRequest.title());
        task.setDescription(taskUpdateRequest.description());
        task.setDeadline(taskUpdateRequest.deadline());
        task.setStatus(Status.valueOf(taskUpdateRequest.status().toUpperCase()));
        task.setPriority(Priority.valueOf(taskUpdateRequest.priority().toUpperCase()));

        taskRepository.save(task);
    }
}
