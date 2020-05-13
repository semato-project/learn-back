package semato.semato_learn.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import semato.semato_learn.model.Publication;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    Optional<List<Publication>> findAllByLecturerIdAndDeletedAtIsNullOrderByIdDesc(Long lecturerId);

    @Query(value = "Select distinct p.id, p.lecturer_id, p.title, p.description, p.created_at, p.updated_at, p.deleted_at from publication p " +
            "join `user` lec on p.lecturer_id = lec.id " +
            "join course c on c.lecturer_id = lec.id " +
            "where c.group_id=:studentGroupId " +
            "and p.deleted_at is null " +
            "order by p.id desc",
            nativeQuery = true)
    Optional<List<Publication>> findAllByStudentGroup(@Param("studentGroupId") Long studentGroupId);
}
