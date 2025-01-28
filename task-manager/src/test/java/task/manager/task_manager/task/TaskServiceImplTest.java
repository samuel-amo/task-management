package task.manager.task_manager.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import task.manager.task_manager.exception.TaskNotFoundException;
import task.manager.task_manager.security.AppUserDetails;
import task.manager.task_manager.user.AppUser;

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
        mockUser.setEmail("john.doe@example.com");

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
                "Prepare Project Proposal",
                "Draft the project proposal for the upcoming client meeting",
                LocalDate.of(2025, 3, 10),
                "HIGH"
        );
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
                101L,
                "Submit Financial Report",
                "Compile and submit the financial report for Q1",
                LocalDate.of(2025, 3, 15),
                "MEDIUM",
                "PENDING"
        );

        when(taskRepository.findTasksByUserAndCriteria(mockUser.getEmail(), Status.PENDING, Priority.MEDIUM, LocalDate.now()))
                .thenReturn(List.of(mockTask));
        when(taskMapper.mapToTaskResponse(mockTask)).thenReturn(mockResponse);

        List<TaskResponse> tasks = taskService.getFilteredTasks(Status.PENDING, Priority.MEDIUM, LocalDate.now());

        assertEquals(1, tasks.size());
        assertEquals(mockResponse, tasks.get(0));
    }

    @Test
    void should_Return_TaskBy_Id_Successfully() {
        Task mockTask = new Task();
        TaskResponse mockResponse = new TaskResponse(
                102L,
                "Plan Team Outing",
                "Organize a team-building outing for next month",
                LocalDate.of(2025, 4, 5),
                "LOW",
                "IN_PROGRESS"
        );

        when(taskRepository.findByTaskIdAndAppUser(102L, mockUser)).thenReturn(Optional.of(mockTask));
        when(taskMapper.mapToTaskResponse(mockTask)).thenReturn(mockResponse);

        TaskResponse response = taskService.getTask(102L);

        assertEquals(mockResponse, response);
    }

    @Test
    void should_Throw_Exception_When_Task_Not_FoundBy_Id() {
        when(taskRepository.findByTaskIdAndAppUser(200L, mockUser)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTask(200L));
    }

    @Test
    void should_Return_All_Tasks_For_AuthenticatedUser() {
        Task mockTask = new Task();
        TaskResponse mockResponse = new TaskResponse(
                103L,
                "Prepare Presentation Slides",
                "Create slides for the product demo presentation",
                LocalDate.of(2025, 2, 20),
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
        when(taskRepository.findByTaskIdAndAppUser(104L, mockUser)).thenReturn(Optional.of(mockTask));

        taskService.deleteTask(104L);

        verify(taskRepository).delete(mockTask);
    }

    @Test
    void should_Throw_Exception_When_Deleting_Non_Existent_Task() {
        when(taskRepository.findByTaskIdAndAppUser(300L, mockUser)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(300L));
    }

    @Test
    void should_Update_Task_Successfully() {
        Task mockTask = new Task();
        TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest(
                105L,
                "Update Client Contact List",
                "Add new client details to the contact database",
                LocalDate.of(2025, 3, 1),
                "LOW",
                "COMPLETED"
        );
        when(taskRepository.findByTaskIdAndAppUser(105L, mockUser)).thenReturn(Optional.of(mockTask));

        taskService.updateTask(105L, taskUpdateRequest);

        assertEquals("Update Client Contact List", mockTask.getTitle());
        assertEquals("Add new client details to the contact database", mockTask.getDescription());
        assertEquals(LocalDate.of(2025, 3, 1), mockTask.getDeadline());
        assertEquals(Status.COMPLETED, mockTask.getStatus());
        assertEquals(Priority.LOW, mockTask.getPriority());
        verify(taskRepository).save(mockTask);
    }

    @Test
    void should_Throw_Exception_When_Updating_Non_Existent_Task() {
        TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest(
                400L,
                "Non-Existent Task",
                "This task does not exist in the system",
                LocalDate.of(2025, 4, 15),
                "MEDIUM",
                "PENDING"
        );
        when(taskRepository.findByTaskIdAndAppUser(400L, mockUser)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(400L, taskUpdateRequest));
    }
}
