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

        studentRepository.save(createStudent("Arnold", "Boczek", 1L));
        studentRepository.save(createStudent("Ferdynand", "Kiepski", 1L));
        studentRepository.save(createStudent("Marian", "Paździoch", 1L));
        studentRepository.save(createStudent("Halina", "Kiepska", 1L));
        studentRepository.save(createStudent("Waldemar", "Kiepski", 1L));
        studentRepository.save(createStudent("Helena", "Pazdzioch", 1L));
        studentRepository.save(createStudent("Rozalia", "Kiepska", 1L));
        studentRepository.save(createStudent("Jolanta", "Kiepska", 1L));
        studentRepository.save(createStudent("Edzio", "Listonosz", 1L));
        studentRepository.save(createStudent("Mariolka", "Kiepska", 1L));

        studentRepository.save(createStudent("Kasia", "Zpodlasia", 2L));
        studentRepository.save(createStudent("Zosia", "ZRivii", 2L));
        studentRepository.save(createStudent("Wiola", "Wiolowa", 2L));
        studentRepository.save(createStudent("Miłka", "Milkowa", 2L));
        studentRepository.save(createStudent("Marian", "Bojowy", 2L));
        studentRepository.save(createStudent("Roman", "Marudny", 2L));
        studentRepository.save(createStudent("Wojtek", "Ambitny", 2L));
        studentRepository.save(createStudent("Michał", "Radosny", 2L));
        studentRepository.save(createStudent("Przemek", "Smutny", 2L));


        studentRepository.save(createStudent("Geralt", "ZRivii", 3L));
        studentRepository.save(createStudent("Jaskier", "Jaskrowy", 3L));
        studentRepository.save(createStudent("Yenefer", "ZVangerbergu", 3L));
        studentRepository.save(createStudent("Ciri", "Jaskolka", 3L));
        studentRepository.save(createStudent("Tissaia", "DeVries", 3L));
        studentRepository.save(createStudent("Vilgefortz", "ZRoggeveen", 3L));
        studentRepository.save(createStudent("Eist", "Tuirseach", 3L));
        studentRepository.save(createStudent("Emil", "Regis", 3L));
        studentRepository.save(createStudent("Francesca", "Findabair", 3L));


    }

    private Student createStudent(String imie, String nazwisko, Long groupId) {
        return Student.builder()
                .group(groupRepository.findById(groupId).orElseThrow(RuntimeException::new))
                .email((imie + nazwisko + "@example.com").toLowerCase())
                .password(passwordEncoder.encode(PASSWORD))
                .firstName(imie)
                .lastName(nazwisko)
                .role(RoleName.ROLE_STUDENT)
                .build();
    }
}
