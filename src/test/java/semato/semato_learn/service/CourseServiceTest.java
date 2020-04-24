package semato.semato_learn.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import semato.semato_learn.SematoLearnApplication;
import semato.semato_learn.controller.payload.CourseRequest;
import semato.semato_learn.controller.payload.TaskRequest;
import semato.semato_learn.model.*;
import semato.semato_learn.model.repository.CourseRepository;
import semato.semato_learn.model.repository.GroupRepository;
import semato.semato_learn.model.repository.UserBaseRepository;

import javax.transaction.Transactional;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SematoLearnApplication.class)
@Transactional
public class CourseServiceTest {

    @Autowired
    private MockService mockService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CourseService courseService;


    private TaskRequest mockTaskRequest(int quantity, Double markWage, TaskType taskType) {
        return new TaskRequest(taskType, quantity, markWage, 0);
    }

    @Test
    public void whenAddCourse_givenCorrectInformation_thenSaveCourse() {
        //given
        Group group = new Group();
        groupRepository.save(group);

        Lecturer lecturer = mockService.mockLecturer();


        List<TaskRequest> taskRequests = new ArrayList<>();
        taskRequests.add(mockTaskRequest(3, 1d, TaskType.LAB));
        taskRequests.add(mockTaskRequest(3, 1d, TaskType.DISCUSSIONS));
        taskRequests.add(mockTaskRequest(3, 1d, TaskType.PROJECT));

        CourseRequest courseRequest = new CourseRequest(group.getId(), "Technologie Obiektowe", "HTML/CSS/JS", taskRequests);

        //when
        courseService.add(courseRequest, lecturer);

        //then
        Optional<List<Course>> allCourses = courseRepository.findAllByLecturerId(lecturer.getId());
        assertTrue(allCourses.isPresent());
        assertEquals(1, allCourses.get().size());
    }

}