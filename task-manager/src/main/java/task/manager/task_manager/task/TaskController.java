package task.manager.task_manager.task;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }


    @PostMapping
    public ResponseEntity<ApiSuccessResponse> createTask(@Valid @RequestBody TaskRequest taskRequest){

        taskService.createTask(taskRequest);
        return new ResponseEntity<>(
                new ApiSuccessResponse("Task created successfully"),
                HttpStatus.CREATED);
    }


    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long taskId) {

        TaskResponse taskResponse = taskService.getTask(taskId);
        return ResponseEntity.ok(taskResponse);
    }


    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks() {

        List<TaskResponse> tasks = taskService.getTasks();
        return ResponseEntity.ok(tasks);
    }


    @DeleteMapping("/{taskId}")
    public ResponseEntity<ApiSuccessResponse> deleteTask(@PathVariable Long taskId) {

        taskService.deleteTask(taskId);

        return ResponseEntity.ok(
                new ApiSuccessResponse("Task deleted successfully"));
    }


    @PutMapping("/{taskId}")
    public ResponseEntity<ApiSuccessResponse> updateTask(@Valid @PathVariable Long taskId, @RequestBody TaskUpdateRequest taskUpdateRequest) {

        taskService.updateTask(taskId, taskUpdateRequest);

        return ResponseEntity.ok(
                new ApiSuccessResponse("Task updated successfully"));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<TaskResponse>> filterTasks(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deadline) {

        List<TaskResponse> tasks = taskService.getFilteredTasks(status, priority, deadline);
        return ResponseEntity.ok(tasks);
    }


}
