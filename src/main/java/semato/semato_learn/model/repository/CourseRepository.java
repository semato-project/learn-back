package semato.semato_learn.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import semato.semato_learn.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
