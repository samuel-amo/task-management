package task.manager.task_manager.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByIdAndOwnerEmail(Long taskId, String ownerEmail);
    List<Task> findByOwnerEmail(String email);
}
