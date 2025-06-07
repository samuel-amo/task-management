package task.manager.task_manager.task;



import jakarta.persistence.*;
import lombok.*;
import task.manager.task_manager.user.AppUser;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private LocalDate deadline;

}
