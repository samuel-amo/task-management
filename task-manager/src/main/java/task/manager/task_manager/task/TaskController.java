package task.manager.task_manager.task;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public final class TaskController {

    private final TaskService taskService;
    private static final String RESPONSE = "success";

    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<ApiSuccessResponse<NoContent>> createTask(@Valid @RequestBody TaskRequest taskRequest) {
        taskService.createTask(taskRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccessResponse<>(RESPONSE, NoContent.INSTANCE));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<TaskResponse>> getTask(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiSuccessResponse<>(RESPONSE, taskService.getTask(id))
        );
    }

    @GetMapping
    public ResponseEntity<ApiSuccessResponse<List<TaskResponse>>> getTasks() {
        return ResponseEntity.ok(
                new ApiSuccessResponse<>(RESPONSE, taskService.getTasks())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<NoContent>> updateTask(@PathVariable Long id, @Valid @RequestBody TaskUpdateRequest taskUpdateRequest) {

        taskService.updateTask(id, taskUpdateRequest);
        return ResponseEntity.ok(
                new ApiSuccessResponse<>(RESPONSE, NoContent.INSTANCE)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<NoContent>> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(
                new ApiSuccessResponse<>(RESPONSE, NoContent.INSTANCE)
        );
    }
}
