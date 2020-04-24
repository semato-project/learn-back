package semato.semato_learn.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import semato.semato_learn.SematoLearnApplication;
import semato.semato_learn.model.*;
import semato.semato_learn.model.repository.*;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SematoLearnApplication.class)
@Transactional
public class GradeManagerServiceTest {

    @Autowired
    private GradeManagerService gradeManagerService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private MockService mockService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private final int TASK_NUMBER = 0;


    @Test
    public void whenAddGrade_givenCorrectInformation_thenSaveGrade() {
        //given
        Group group = new Group();
        Lecturer lecturer = mockService.mockLecturer();
        Course course = mockService.mockCourse(lecturer);

        Set<Course> courses = new HashSet<>();
        courses.add(course);
        group.setCourses(courses);
        lecturer.setCourses(courses);
        groupRepository.save(group);
        Student student = mockService.mockStudent(group);
        Task task = mockService.mockTask(course);

        //when
        gradeManagerService.addGrade(student.getId(), task.getId(), TASK_NUMBER, 5, lecturer.getId());

        //then
        Optional<Grade> grade = gradeRepository.findByStudentIdAndTaskIdAndTaskNumber(student.getId(), task.getId(), 0);
        assertTrue(grade.isPresent());
        assertEquals(grade.get().getGradeValue(), 5);
    }

    @Test
    public void whenAddGradeTwiceTimesForTheSameTaskNumber_givenCorrectInformation_thenThrowIllegalArgumentException() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("This student actually have the grade for this task number!");

        //given
        Group group = new Group();
        Lecturer lecturer = mockService.mockLecturer();
        Course course = mockService.mockCourse(lecturer);

        Set<Course> courses = new HashSet<>();
        courses.add(course);
        group.setCourses(courses);
        lecturer.setCourses(courses);
        groupRepository.save(group);

        Student student = mockService.mockStudent(group);

        Task task = mockService.mockTask(course);

        //when
        gradeManagerService.addGrade(student.getId(), task.getId(), TASK_NUMBER, 5, lecturer.getId());
        gradeManagerService.addGrade(student.getId(), task.getId(), TASK_NUMBER, 5, lecturer.getId());
    }

    @Test
    public void whenAddGrade_givenCorrectInformationWithoutTask_thenThrowIllegalArgumentException() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Task not found!");

        //given
        Group group = new Group();
        Lecturer lecturer = mockService.mockLecturer();
        Course course = mockService.mockCourse(lecturer);

        Set<Course> courses = new HashSet<>();
        courses.add(course);
        group.setCourses(courses);
        lecturer.setCourses(courses);
        groupRepository.save(group);

        Student student = mockService.mockStudent(group);

        //when
        gradeManagerService.addGrade(student.getId(), 0, TASK_NUMBER, 5, lecturer.getId());
    }

    @Test
    public void whenAddGrade_givenIncorrectInformation_thenThrowIllegalArgumentException() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Task isn't manage by this lecturer!");

        //given
        Group group = new Group();
        Lecturer lecturer = mockService.mockLecturer();
        Course course = mockService.mockCourse();

        Set<Course> courses = new HashSet<>();
        courses.add(course);
        group.setCourses(courses);
        groupRepository.save(group);

        Student student = mockService.mockStudent(group);

        Task task = mockService.mockTask(course);

        //when
        gradeManagerService.addGrade(student.getId(), task.getId(), TASK_NUMBER, 5, lecturer.getId());
    }

    @Test
    public void whenAddGrade_givenIncorrectInformationAboutStudent_thenThrowIllegalArgumentException() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Student not found!");

        //given
        Group group = new Group();
        Lecturer lecturer = mockService.mockLecturer();
        Course course = mockService.mockCourse(lecturer);

        Set<Course> courses = new HashSet<>();
        courses.add(course);
        group.setCourses(courses);
        lecturer.setCourses(courses);
        groupRepository.save(group);

        Task task = mockService.mockTask(course);

        //when
        gradeManagerService.addGrade(0, task.getId(), TASK_NUMBER, 5, lecturer.getId());
    }

    @Test
    public void whenAddGrade_givenIncorrectTaskNumber_toSmall_thenThrowIllegalArgumentException() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Task number is incorrect. Max task number is: 3");

        //given
        Group group = new Group();
        Lecturer lecturer = mockService.mockLecturer();
        Course course = mockService.mockCourse(lecturer);

        Set<Course> courses = new HashSet<>();
        courses.add(course);
        group.setCourses(courses);
        lecturer.setCourses(courses);
        groupRepository.save(group);

        Student student = mockService.mockStudent(group);

        Task task = mockService.mockTask(course);

        //when
        gradeManagerService.addGrade(student.getId(), task.getId(), -1, 5, lecturer.getId());
    }

    @Test
    public void whenAddGrade_givenIncorrectTaskNumber_tooBig_thenThrowIllegalArgumentException() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Task number is incorrect. Max task number is: 3");

        //given
        Group group = new Group();
        Lecturer lecturer = mockService.mockLecturer();
        Course course = mockService.mockCourse(lecturer);

        Set<Course> courses = new HashSet<>();
        courses.add(course);
        group.setCourses(courses);
        lecturer.setCourses(courses);
        groupRepository.save(group);

        Student student = mockService.mockStudent(group);

        Task task = mockService.mockTask(course);

        //when
        gradeManagerService.addGrade(student.getId(), task.getId(), 4, 5, lecturer.getId());
    }

    @Test
    public void whenEditGrade_givenCorrectInformation_thenUpdateGrade() {

        //given
        Group group = new Group();
        Lecturer lecturer = mockService.mockLecturer();
        Course course = mockService.mockCourse(lecturer);

        Set<Course> courses = new HashSet<>();
        courses.add(course);
        group.setCourses(courses);
        lecturer.setCourses(courses);
        groupRepository.save(group);

        Student student = mockService.mockStudent(group);

        Task task = mockService.mockTask(course);

        Grade grade = mockService.mockGrade(task, TASK_NUMBER, student);
        gradeRepository.save(grade);

        //when
        gradeManagerService.editGrade(student.getId(), task.getId(), TASK_NUMBER, 4.5, lecturer.getId());

        //then
        Optional<Grade> fetchGrade = gradeRepository.findByStudentIdAndTaskIdAndTaskNumber(student.getId(), task.getId(), 0);
        assertTrue(fetchGrade .isPresent());
        assertEquals(fetchGrade.get().getGradeValue(), 4.5);
    }

}