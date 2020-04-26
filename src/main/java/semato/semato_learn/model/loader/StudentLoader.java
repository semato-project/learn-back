package semato.semato_learn.model.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import semato.semato_learn.model.RoleName;
import semato.semato_learn.model.Student;
import semato.semato_learn.model.repository.GroupRepository;
import semato.semato_learn.model.repository.UserBaseRepository;

import static semato.semato_learn.model.loader.LecturerLoader.PASSWORD;

@Component
@Profile("!test")
@Order(5)
public class StudentLoader implements ApplicationRunner {

    @Autowired
    private UserBaseRepository<Student> studentRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        studentRepository.save(Student.builder()
                .group(groupRepository.findById(1L).orElseThrow(RuntimeException::new))
                .email("arnoldBoczek@gmail.com")
                .password(passwordEncoder.encode(PASSWORD))
                .firstName("Arnold")
                .lastName("Boczek")
                .role(RoleName.ROLE_STUDENT)
                .build());
        studentRepository.save(Student.builder()
                .group(groupRepository.findById(1L).orElseThrow(RuntimeException::new))
                .email("ferdynandKiepski@gmail.com")
                .password(passwordEncoder.encode(PASSWORD))
                .firstName("Ferdynand")
                .lastName("Kiepski")
                .role(RoleName.ROLE_STUDENT)
                .build());

        studentRepository.save(Student.builder()
                .group(groupRepository.findById(2L).orElseThrow(RuntimeException::new))
                .email("marianPazdzioch@gmail.com")
                .password(passwordEncoder.encode(PASSWORD))
                .firstName("Marian")
                .lastName("Paździoch")
                .role(RoleName.ROLE_STUDENT)
                .build());
        studentRepository.save(Student.builder()
                .group(groupRepository.findById(2L).orElseThrow(RuntimeException::new))
                .email("halinaKiepska@gmail.com")
                .password(passwordEncoder.encode(PASSWORD))
                .firstName("Halina")
                .lastName("Kiepska")
                .role(RoleName.ROLE_STUDENT)
                .build());

        studentRepository.save(Student.builder()
                .group(groupRepository.findById(3L).orElseThrow(RuntimeException::new))
                .email("waldemarKiepski@gmail.com")
                .password(passwordEncoder.encode(PASSWORD))
                .firstName("Waldemar")
                .lastName("Kiepski")
                .role(RoleName.ROLE_STUDENT)
                .build());
        studentRepository.save(Student.builder()
                .group(groupRepository.findById(3L).orElseThrow(RuntimeException::new))
                .email("helenaPazdzioch@gmail.com")
                .password(passwordEncoder.encode(PASSWORD))
                .firstName("Helena")
                .lastName("Paździoch")
                .role(RoleName.ROLE_STUDENT)
                .build());
    }
}
