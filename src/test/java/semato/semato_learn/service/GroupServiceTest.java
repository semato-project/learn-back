package semato.semato_learn.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import semato.semato_learn.SematoLearnApplication;
import semato.semato_learn.controller.payload.GroupResponse;
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
        List<GroupResponse> allGroups = groupService.getAllGroups();
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

        List<GroupResponse> allGroups = groupService.getAllGroups();
        assertEquals(2, allGroups.size());
    }

    @Test
    public void shouldReturnGroup() {
        Group group = Group.builder()
                .academicYear("I")
                .faculty("WIEiK")
                .field("Elektornika")
                .build();

        groupRepository.save(group);
        assertEquals(group.getId(), groupService.getGroup(1L).getId());
    }

    @Test
    public void shouldThrowException() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Group not found!");
        groupService.getGroup(1L);
    }


}