package semato.semato_learn.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import semato.semato_learn.model.ProjectGroup;
import semato.semato_learn.model.Student;
import semato.semato_learn.model.Task;

import java.util.Optional;

@Repository
public interface ProjectGroupRepository extends JpaRepository<ProjectGroup, Long> {
    public Optional<ProjectGroup> findByStudentsAndTask(Student student, Task task);
}
