package semato.semato_learn.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import semato.semato_learn.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
