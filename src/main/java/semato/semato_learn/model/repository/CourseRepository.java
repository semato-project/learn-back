package semato.semato_learn.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import semato.semato_learn.model.Course;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<List<Course>> findAllByLecturerId(Long lecturerId);

    @Query(value = "Select c.* from course c " +
            "join \"user\" st on st.group_id = c.group_id " +
            "where st.id = :studentId", nativeQuery = true)
    Optional<List<Course>> findAllByStudentGroup(Long studentId);
}
