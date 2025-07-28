package task.manager.task_manager.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import task.manager.task_manager.auth.user.AppUser;
import task.manager.task_manager.auth.user.AppUserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final AppUserRepository appUserRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, AppUserRepository appUserRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.appUserRepository = appUserRepository;
    }

    @Override
    @Transactional
    public void createTask(TaskRequest taskRequest) {
        AppUser currentUser = getCurrentAuthenticatedUser();
        Task task = taskMapper.mapToTask(taskRequest);
        task.setOwner(currentUser);

        taskRepository.save(task);
        LOGGER.info("Task '{}' created for user '{}'", task.getTitle(), currentUser.getEmail());
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponse getTask(Long id) {
        Task task = findTaskByIdAndValidateOwnership(id);
        return taskMapper.mapToTaskResponse(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasks() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        return taskRepository.findByOwnerEmail(userEmail)
                .stream()
                .map(taskMapper::mapToTaskResponse)
                .toList();
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {

        Task task = findTaskByIdAndValidateOwnership(id);
        taskRepository.delete(task);
        LOGGER.info("Task with id {} deleted by user '{}'", id, task.getOwner().getEmail());
    }

    @Override
    @Transactional
    public void updateTask(Long taskId, TaskUpdateRequest taskUpdateRequest) {
        Task task = findTaskByIdAndValidateOwnership(taskId);


        taskMapper.updateTaskFromRequest(taskUpdateRequest, task);

        taskRepository.save(task);
        LOGGER.info("Task with id {} updated by user '{}'", taskId, task.getOwner().getEmail());
    }


    private Task findTaskByIdAndValidateOwnership(Long taskId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        return taskRepository.findByIdAndOwnerEmail(taskId, userEmail)
                .orElseThrow(() -> new TaskNotFoundException(
                        String.format("Task not found with id: %d, or you do not have permission to access it.", taskId)
                ));
    }

    private AppUser getCurrentAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found in the database."));
    }
}