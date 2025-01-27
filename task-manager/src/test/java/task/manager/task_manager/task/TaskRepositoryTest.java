package task.manager.task_manager.task;

import com.example.task_management.user.AppUser;
import com.example.task_management.user.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    void should_FindTasks_By_AppUser() {

        AppUser user = new AppUser();
        user.setEmail("user1@example.com");
        user = appUserRepository.save(user);

        Task task = new Task();
        task.setAppUser(user);
        taskRepository.save(task);


        List<Task> tasks = taskRepository.findByAppUser(user);


        assertThat(tasks).hasSize(1);
        assertThat(tasks.get(0).getAppUser().getEmail()).isEqualTo("user1@example.com");
    }

    @Test
    void should_FindTask_By_TaskId_And_AppUser() {

        AppUser user = new AppUser();
        user.setEmail("user2@example.com");
        user = appUserRepository.save(user);

        Task task = new Task();
        task.setAppUser(user);
        task = taskRepository.save(task);


        Optional<Task> foundTask = taskRepository.findByTaskIdAndAppUser(task.getTaskId(), user);


        assertThat(foundTask).isPresent();
        assertThat(foundTask.get().getAppUser().getEmail()).isEqualTo("user2@example.com");
    }


    @Test

    void should_FindTasks_By_User_And_Criteria() {

        AppUser user = new AppUser();
        user.setEmail("user3@example.com");
        user = appUserRepository.save(user);

        Task task1 = new Task();
        task1.setAppUser(user);
        task1.setStatus(Status.PENDING);
        task1.setPriority(Priority.HIGH);
        task1.setDeadline(LocalDate.of(2025, 1, 30));

        Task task2 = new Task();
        task2.setAppUser(user);
        task2.setStatus(Status.COMPLETED);
        task2.setPriority(Priority.MEDIUM);
        task2.setDeadline(LocalDate.of(2025, 2, 1));

        taskRepository.save(task1);
        taskRepository.save(task2);


        List<Task> tasks = taskRepository.findTasksByUserAndCriteria(
                "user3@example.com",
                Status.PENDING,
                null,
                null
        );


        assertThat(tasks).hasSize(1);
        assertThat(tasks.getFirst().getStatus()).isEqualTo(Status.PENDING);
        assertThat(tasks.getFirst().getPriority()).isEqualTo(Priority.HIGH);
    }
}
