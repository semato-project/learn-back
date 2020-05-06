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

@Component
@Profile("!test")
@Order(2)
public class LecturerLoader implements ApplicationRunner {

    @Autowired
    private UserBaseRepository<Lecturer> lecturerUserBaseRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    static final String PROFESORDOKTOR_EMAIL = "profesordoktor@example.com";
    static final String DRSTRANGE_EMAIL = "drstrange@example.com";
    static final String PASSWORD = "qwerty";

    @Override
    public void run(ApplicationArguments args) {
            createLecturer(PROFESORDOKTOR_EMAIL, "Profesor", "Doktor");
            createLecturer(DRSTRANGE_EMAIL, "Dr", "Strange");
    }

    private void createLecturer(String email, String firstName, String lastName) {
        if(! lecturerUserBaseRepository.findByEmail(email).isPresent()) {
            Lecturer lecturer = new Lecturer();
            lecturer.setEmail(email);
            lecturer.setFirstName(firstName);
            lecturer.setLastName(lastName);
            lecturer.setPassword(passwordEncoder.encode(PASSWORD));
            lecturer.setRole(RoleName.ROLE_LECTURER);
            lecturerUserBaseRepository.save(lecturer);
        }
    }
}
