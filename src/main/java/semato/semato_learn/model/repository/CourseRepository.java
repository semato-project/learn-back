package semato.semato_learn.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import semato.semato_learn.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<List<Course>> findAllByLecturerId(Long lecturerId);
}
