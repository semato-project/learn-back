package semato.semato_learn.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import semato.semato_learn.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
