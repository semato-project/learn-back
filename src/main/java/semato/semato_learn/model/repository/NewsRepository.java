package semato.semato_learn.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import semato.semato_learn.model.News;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    Optional<List<News>> findAllByLecturerId(Long lecturerId);
}
