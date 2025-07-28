package task.manager.task_manager.task;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import task.manager.task_manager.common.responses.ApiSuccessResponse;
import task.manager.task_manager.common.responses.JSendSuccessResponse;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @PostMapping
    public ResponseEntity<JSendSuccessResponse<Void>> createTask(@Valid @RequestBody TaskRequest taskRequest) {
        taskService.createTask(taskRequest);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(JSendSuccessResponse.empty());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JSendSuccessResponse<TaskResponse>> getTask(@PathVariable Long id) {
        TaskResponse task = taskService.getTask(id);
        return ResponseEntity.ok(JSendSuccessResponse.of(task));
    }

    @GetMapping
    public ResponseEntity<JSendSuccessResponse<List<TaskResponse>>> getTasks() {
        List<TaskResponse> tasks = taskService.getTasks();
        return ResponseEntity.ok(JSendSuccessResponse.of(tasks));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JSendSuccessResponse<Void>> updateTask(@PathVariable Long id, @Valid @RequestBody TaskUpdateRequest taskUpdateRequest) {
        taskService.updateTask(id, taskUpdateRequest);

        return ResponseEntity.ok(JSendSuccessResponse.empty());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JSendSuccessResponse<Void>> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);

        return ResponseEntity.ok(JSendSuccessResponse.empty());
    }
}