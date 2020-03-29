package semato.semato_learn.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import semato.semato_learn.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
