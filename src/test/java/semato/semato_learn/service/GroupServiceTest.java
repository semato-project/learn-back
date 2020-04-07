package semato.semato_learn.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import semato.semato_learn.SematoLearnApplication;
import semato.semato_learn.model.Group;
import semato.semato_learn.model.repository.GroupRepository;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SematoLearnApplication.class)
@Transactional
public class GroupServiceTest {

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupRepository groupRepository;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void shouldReturnEmptyList() {
        List<Group> allGroups = groupService.getAllGroups();
        assertTrue(allGroups.isEmpty());
    }

    @Test
    public void shouldReturnList() {
        groupRepository.save(Group.builder()
                .academicYear("I")
                .faculty("WIEiK")
                .field("Elektornika")
                .build()
        );
        groupRepository.save(Group.builder()
                .academicYear("I")
                .faculty("WIEiK")
                .field("Informatyka")
                .build()
        );

        List<Group> allGroups = groupService.getAllGroups();
        assertEquals(2, allGroups.size());
    }

    @Test
    public void shouldReturnGroup() {
        groupRepository.save(Group.builder()
                .academicYear("I")
                .faculty("WIEiK")
                .field("Elektornika")
                .build()
        );
        groupRepository.save(Group.builder()
                .academicYear("I")
                .faculty("WIEiK")
                .field("Informatyka")
                .build()
        );

        Group group = groupService.getGroup(1L);
        assertEquals(1L, group.getId());
    }

    @Test
    public void shouldThrowException() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Group not found!");
        Group group = groupService.getGroup(1L);
    }


}