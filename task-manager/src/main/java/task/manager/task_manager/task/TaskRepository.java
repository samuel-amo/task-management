package task.manager.task_manager.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import task.manager.task_manager.user.AppUser;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAppUser(AppUser appUser);

    Optional<Task> findByTaskIdAndAppUser(Long taskId, AppUser appUser);


    @Query("""
        SELECT t 
        FROM Task t 
        WHERE t.appUser.email = :email 
        AND (:status IS NULL OR t.status = :status) 
        AND (:priority IS NULL OR t.priority = :priority) 
        AND (:deadline IS NULL OR t.deadline <= :deadline)
        """)
    List<Task> findTasksByUserAndCriteria(
            @Param("email") String email,
            @Param("status") Status status,
            @Param("priority") Priority priority,
            @Param("deadline") LocalDate deadline);

}
