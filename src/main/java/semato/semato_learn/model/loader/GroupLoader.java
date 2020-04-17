package semato.semato_learn.model.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import semato.semato_learn.model.Group;
import semato.semato_learn.model.repository.GroupRepository;

@Component
@Profile("!test")
@Order(1)
public class GroupLoader implements ApplicationRunner {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public void run(ApplicationArguments args) {
        groupRepository.save(Group.builder()
                .academicYear("VI")
                .faculty("WIEiK")
                .field("Informatyka")
                .build()
        );

        groupRepository.save(Group.builder()
                .academicYear("V")
                .faculty("WIEiK")
                .field("Informatyka")
                .build()
        );

        groupRepository.save(Group.builder()
                .academicYear("IV")
                .faculty("WIEiK")
                .field("Informatyka")
                .build()
        );
    }
}
