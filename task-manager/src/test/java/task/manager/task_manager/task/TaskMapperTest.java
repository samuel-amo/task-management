package task.manager.task_manager.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskMapperTest {

    private TaskMapper taskMapper;

    @BeforeEach
    void setUp() {
        taskMapper = new TaskMapper();
    }

    @Test
    void should_Map_Task_Request_To_Task() {
        TaskRequest taskRequest = new TaskRequest(
                "Test Task",
                "This is a test task description",
                LocalDate.of(2025, 4, 25),
                "HIGH"
        );

        Task task = taskMapper.mapToTask(taskRequest);

        assertEquals("Test Task", task.getTitle());
        assertEquals("This is a test task description", task.getDescription());
        assertEquals(LocalDate.of(2025, 4, 25), task.getDeadline());
        assertEquals(Status.PENDING, task.getStatus());
        assertEquals(Priority.HIGH, task.getPriority());
    }

    @Test
    void should_Map_Task_To_TaskResponse() {
        Task task = Task.builder()
                .taskId(1L)
                .title("Test Task")
                .description("This is a test task description")
                .deadline(LocalDate.of(2025, 4, 25))
                .status(Status.COMPLETED)
                .priority(Priority.HIGH)
                .build();

        TaskResponse taskResponse = taskMapper.mapToTaskResponse(task);

        assertEquals(1L, taskResponse.taskId());
        assertEquals("Test Task", taskResponse.title());
        assertEquals("This is a test task description", taskResponse.description());
        assertEquals(LocalDate.of(2025, 4, 25), taskResponse.deadline());
        assertEquals("HIGH", taskResponse.priority());
        assertEquals("COMPLETED", taskResponse.status());
    }
}
