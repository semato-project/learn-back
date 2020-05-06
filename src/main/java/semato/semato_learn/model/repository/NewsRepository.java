package semato.semato_learn.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import semato.semato_learn.model.News;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    Optional<List<News>> findAllByLecturerIdAndDeletedAtIsNullOrderByIdDesc(Long lecturerId);

    @Query(value = "Select n.id, n.lecturer_id, n.title, n.description, n.created_at, n.updated_at, n.deleted_at from news n " +
            "join `user` lec on n.lecturer_id = lec.id " +
            "join course c on c.lecturer_id = lec.id " +
            "where c.group_id=:studentGroupId " +
            "and n.deleted_at is null " +
            "order by n.id desc",
            nativeQuery = true)
    Optional<List<News>> findAllByStudentGroup(@Param("studentGroupId") Long studentGroupId);
}
