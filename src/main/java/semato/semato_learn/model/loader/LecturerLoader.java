package semato.semato_learn.model.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import semato.semato_learn.model.Lecturer;
import semato.semato_learn.model.RoleName;
import semato.semato_learn.model.repository.UserBaseRepository;


import java.time.LocalDate;
import java.util.Collections;

@Component
@Profile("!test")
@Order(2)
public class LecturerLoader implements ApplicationRunner {

    @Autowired
    private UserBaseRepository<Lecturer> lecturerUserBaseRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public static final String EMAIL = "profesordoktor@example.com";
    public static final String PASSWORD = "qwerty";

    @Override
    public void run(ApplicationArguments args) {

        if(! lecturerUserBaseRepository.findByEmail(EMAIL).isPresent()) {

            Lecturer lecturer = new Lecturer();
            lecturer.setEmail(EMAIL);
            lecturer.setFirstName("Profesor");
            lecturer.setLastName("Doktor");
            lecturer.setPassword(passwordEncoder.encode(PASSWORD));
            lecturer.setRole(RoleName.ROLE_LECTURER);
            lecturerUserBaseRepository.save(lecturer);


        }
    }
}
