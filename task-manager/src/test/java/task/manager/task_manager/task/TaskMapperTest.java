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
                "Complete Sprint Planning",
                "Finalize the sprint backlog and assign tasks to the team",
                LocalDate.of(2025, 3, 15),
                "MEDIUM"
        );

        Task task = taskMapper.mapToTask(taskRequest);

        assertEquals("Complete Sprint Planning", task.getTitle());
        assertEquals("Finalize the sprint backlog and assign tasks to the team", task.getDescription());
        assertEquals(LocalDate.of(2025, 3, 15), task.getDeadline());
        assertEquals(Status.PENDING, task.getStatus());
        assertEquals(Priority.MEDIUM, task.getPriority());
    }

    @Test
    void should_Map_Task_To_TaskResponse() {
        Task task = Task.builder()
                .taskId(1001L)
                .title("Review Quarterly Report")
                .description("Analyze financial metrics and provide feedback to the finance team")
                .deadline(LocalDate.of(2025, 2, 28))
                .status(Status.COMPLETED)
                .priority(Priority.HIGH)
                .build();

        TaskResponse taskResponse = taskMapper.mapToTaskResponse(task);

        assertEquals(1001L, taskResponse.taskId());
        assertEquals("Review Quarterly Report", taskResponse.title());
        assertEquals("Analyze financial metrics and provide feedback to the finance team", taskResponse.description());
        assertEquals(LocalDate.of(2025, 2, 28), taskResponse.deadline());
        assertEquals("HIGH", taskResponse.priority());
        assertEquals("COMPLETED", taskResponse.status());
    }
}
