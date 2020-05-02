package semato.semato_learn.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import semato.semato_learn.SematoLearnApplication;
import semato.semato_learn.controller.payload.CourseRequest;
import semato.semato_learn.controller.payload.CourseResponse;
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


    private TaskRequest mockTaskRequest(int quantity, Double markWeight, TaskType taskType) {
        return new TaskRequest(taskType, quantity, markWeight, 0);
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

    @Test
    public void whenGetAllCourses_byUser_givenGroupsAndCourses_thenReturnCoursesDependingOnRole() {
        //given
        Group groupOne = new Group();
        groupRepository.save(groupOne);

        Group groupTwo = new Group();
        groupRepository.save(groupTwo);

        Lecturer lecturer = mockService.mockLecturer();
        Student student = mockService.mockStudent(groupOne);

        List<TaskRequest> mockTasks = new ArrayList<>();
        mockTasks.add(mockTaskRequest(3, 1d, TaskType.LAB));
        mockTasks.add(mockTaskRequest(3, 1d, TaskType.DISCUSSIONS));
        mockTasks.add(mockTaskRequest(3, 1d, TaskType.PROJECT));

        CourseRequest courseForGroupOne = new CourseRequest(groupOne.getId(), "Technologie Obiektowe", "HTML/CSS/JS", mockTasks);
        CourseRequest courseForGroupTwo = new CourseRequest(groupTwo.getId(), "Systemy rozproszone", "Nauka o pvm", mockTasks);
        courseService.add(courseForGroupOne, lecturer);
        courseService.add(courseForGroupTwo, lecturer);

        //when
        List<CourseResponse> studentCourses = courseService.getAll(student);
        List<CourseResponse> lecturerCourses = courseService.getAll(lecturer);

        //then
        assertEquals(1, studentCourses.size());
        assertEquals(2, lecturerCourses.size());
        assertEquals("Technologie Obiektowe", studentCourses.get(0).getName());
        assertEquals("Technologie Obiektowe", lecturerCourses.get(0).getName());
        assertEquals("Systemy rozproszone", lecturerCourses.get(1).getName());
    }

}