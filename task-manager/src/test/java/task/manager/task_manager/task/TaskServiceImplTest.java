package task.manager.task_manager.task;

import com.example.task_management.exception.TaskNotFoundException;
import com.example.task_management.security.AppUserDetails;
import com.example.task_management.user.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private AppUser mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new AppUser();
        mockUser.setUserId(1L);
        mockUser.setEmail("test@example.com");

        AppUserDetails userDetails = new AppUserDetails(mockUser);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void should_Create_TaskSuccessfully() {
        TaskRequest taskRequest = new TaskRequest(
                "Task Title",
                "Task Description",
                LocalDate.now(),
                "HIGH");
        Task mockTask = new Task();
        when(taskMapper.mapToTask(taskRequest)).thenReturn(mockTask);

        taskService.createTask(taskRequest);

        verify(taskRepository).save(mockTask);
        assertEquals(mockUser, mockTask.getAppUser());
    }

    @Test
    void should_Return_FilteredTasks_By_Criteria() {
        Task mockTask = new Task();
        TaskResponse mockResponse = new TaskResponse(
                1L,
                "cleaning",
                "cleaning the house",
                LocalDate.of(2025, 4, 25),
                "HIGH",
                "COMPLETED"
        );

        when(taskRepository.findTasksByUserAndCriteria(mockUser.getEmail(), Status.PENDING, Priority.HIGH, LocalDate.now()))
                .thenReturn(List.of(mockTask));
        when(taskMapper.mapToTaskResponse(mockTask)).thenReturn(mockResponse);

        List<TaskResponse> tasks = taskService.getFilteredTasks(Status.PENDING, Priority.HIGH, LocalDate.now());

        assertEquals(1, tasks.size());
        assertEquals(mockResponse, tasks.getFirst());
    }

    @Test
    void should_Return_TaskBy_Id_Successfully() {
        Task mockTask = new Task();
        TaskResponse mockResponse = new TaskResponse(
                1L,
                "cleaning",
                "cleaning the house",
                LocalDate.of(2025, 4, 25),
                "HIGH",
                "COMPLETED"
        );

        when(taskRepository.findByTaskIdAndAppUser(1L, mockUser)).thenReturn(Optional.of(mockTask));
        when(taskMapper.mapToTaskResponse(mockTask)).thenReturn(mockResponse);

        TaskResponse response = taskService.getTask(1L);

        assertEquals(mockResponse, response);
    }

    @Test
    void should_Throw_Exception_When_Task_Not_FoundBy_Id() {
        when(taskRepository.findByTaskIdAndAppUser(1L, mockUser)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTask(1L));
    }

    @Test
    void should_Return_All_Tasks_For_AuthenticatedUser() {
        Task mockTask = new Task();
        TaskResponse mockResponse = new TaskResponse(
                1L,
                "cleaning",
                "cleaning the house",
                LocalDate.of(2025, 4, 25),
                "HIGH",
                "COMPLETED"
        );
        when(taskRepository.findByAppUser(mockUser)).thenReturn(List.of(mockTask));
        when(taskMapper.mapToTaskResponse(mockTask)).thenReturn(mockResponse);

        List<TaskResponse> tasks = taskService.getTasks();

        assertEquals(1, tasks.size());
        assertEquals(mockResponse, tasks.get(0));
    }

    @Test
    void should_Delete_Task_Successfully() {
        Task mockTask = new Task();
        when(taskRepository.findByTaskIdAndAppUser(1L, mockUser)).thenReturn(Optional.of(mockTask));

        taskService.deleteTask(1L);

        verify(taskRepository).delete(mockTask);
    }

    @Test
    void should_Throw_Exception_When_Deleting_Non_Existent_Task() {
        when(taskRepository.findByTaskIdAndAppUser(1L, mockUser)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(1L));
    }

    @Test
    void should_Update_Task_Successfully() {
        Task mockTask = new Task();
        TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest(
                1L,
                "Updated Title",
                "Updated Description",
                LocalDate.now(),
                "LOW",
                "COMPLETED"
        );
        when(taskRepository.findByTaskIdAndAppUser(1L, mockUser)).thenReturn(Optional.of(mockTask));

        taskService.updateTask(1L, taskUpdateRequest);

        assertEquals("Updated Title", mockTask.getTitle());
        assertEquals("Updated Description", mockTask.getDescription());
        assertEquals(LocalDate.now(), mockTask.getDeadline());
        assertEquals(Status.COMPLETED, mockTask.getStatus());
        assertEquals(Priority.LOW, mockTask.getPriority());
        verify(taskRepository).save(mockTask);
    }

    @Test
    void should_Throw_Exception_When_Updating_Non_Existent_Task() {
        TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest(
                1L,
                "Updated Title",
                "Updated Description",
                LocalDate.now(),
                "LOW",
                "COMPLETED"
        );
        when(taskRepository.findByTaskIdAndAppUser(1L, mockUser)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(1L, taskUpdateRequest));
    }
}
