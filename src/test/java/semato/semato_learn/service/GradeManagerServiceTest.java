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
    private UserBaseRepository<Student> studentRepository;

    @Autowired
    private UserBaseRepository<Lecturer> lecturerRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    final int TASK_NUMBER = 0;

    private Lecturer mockLecturer() {
        return Lecturer.builder()
                .firstName("Karol")
                .lastName("Krawczyk")
                .password("tajnehaslo")
                .role(RoleName.ROLE_LECTURER)
                .email("karol.krawczyk@semato.pl")
                .build();
    }

    private Course mockCourse() {
        return Course.builder()
                .name("Kurs migowego")
                .description("Wypasiony kurs migowego")
                .build();
    }

    private Student mockStudent(Group group) {
        return Student.builder()
                .group(group)
                .email("mikolajek@semato.com")
                .password("tajneHaslo")
                .firstName("Mikolajek")
                .lastName("Klucznik")
                .role(RoleName.ROLE_STUDENT)
                .build();
    }

    private Task mockTask(Course course) {
        return Task.builder()
                .course(course)
                .quantity(3)
                .markWage(1)
                .taskType(TaskType.LAB)
                .build();
    }

    @Test
    public void whenAddGrade_givenCorrectInformation_thenSaveGrade() {
        //given
        Group group = new Group();

        Lecturer lecturer = mockLecturer();
        lecturerRepository.save(lecturer);

        Course course = mockCourse();
        course.setLecturer(lecturer);
        courseRepository.save(course);

        Set<Course> courses = new HashSet<>();
        courses.add(course);
        group.setCourses(courses);
        lecturer.setCourses(courses);
        groupRepository.save(group);

        Student student = mockStudent(group);
        studentRepository.save(student);

        Task task = mockTask(course);
        taskRepository.save(task);

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

        Lecturer lecturer = mockLecturer();
        lecturerRepository.save(lecturer);

        Course course = mockCourse();
        course.setLecturer(lecturer);
        courseRepository.save(course);

        Set<Course> courses = new HashSet<>();
        courses.add(course);
        group.setCourses(courses);
        lecturer.setCourses(courses);
        groupRepository.save(group);

        Student student = mockStudent(group);
        studentRepository.save(student);

        Task task = mockTask(course);
        taskRepository.save(task);

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

        Lecturer lecturer = mockLecturer();
        lecturerRepository.save(lecturer);

        Course course = mockCourse();
        course.setLecturer(lecturer);
        courseRepository.save(course);

        Set<Course> courses = new HashSet<>();
        courses.add(course);
        group.setCourses(courses);
        lecturer.setCourses(courses);
        groupRepository.save(group);

        Student student = mockStudent(group);
        studentRepository.save(student);

        //when
        gradeManagerService.addGrade(student.getId(), 0, TASK_NUMBER, 5, lecturer.getId());
    }

    @Test
    public void whenAddGrade_givenIncorrectInformation_thenThrowIllegalArgumentException() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Task isn't manage by this lecturer!");

        //given
        Group group = new Group();

        Lecturer lecturer = mockLecturer();
        lecturerRepository.save(lecturer);

        Course course = mockCourse();
        courseRepository.save(course);

        Set<Course> courses = new HashSet<>();
        courses.add(course);
        group.setCourses(courses);
        groupRepository.save(group);

        Student student = mockStudent(group);
        studentRepository.save(student);

        Task task = mockTask(course);
        taskRepository.save(task);

        //when
        gradeManagerService.addGrade(student.getId(), task.getId(), TASK_NUMBER, 5, lecturer.getId());
    }

    @Test
    public void whenAddGrade_givenIncorrectInformationAboutStudent_thenThrowIllegalArgumentException() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Student not found!");

        //given
        Group group = new Group();

        Lecturer lecturer = mockLecturer();
        lecturerRepository.save(lecturer);

        Course course = mockCourse();
        course.setLecturer(lecturer);
        courseRepository.save(course);

        Set<Course> courses = new HashSet<>();
        courses.add(course);
        group.setCourses(courses);
        lecturer.setCourses(courses);
        groupRepository.save(group);

        Task task = mockTask(course);
        taskRepository.save(task);

        //when
        gradeManagerService.addGrade(0, task.getId(), TASK_NUMBER, 5, lecturer.getId());
    }

    @Test
    public void whenAddGrade_givenIncorrectTaskNumber_toSmall_thenThrowIllegalArgumentException() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Task number is incorrect. Max task number is: 3");

        //given
        Group group = new Group();

        Lecturer lecturer = mockLecturer();
        lecturerRepository.save(lecturer);

        Course course = mockCourse();
        course.setLecturer(lecturer);
        courseRepository.save(course);

        Set<Course> courses = new HashSet<>();
        courses.add(course);
        group.setCourses(courses);
        lecturer.setCourses(courses);
        groupRepository.save(group);

        Student student = mockStudent(group);
        studentRepository.save(student);

        Task task = mockTask(course);
        taskRepository.save(task);

        //when
        gradeManagerService.addGrade(student.getId(), task.getId(), -1, 5, lecturer.getId());
    }

    @Test
    public void whenAddGrade_givenIncorrectTaskNumber_tooBig_thenThrowIllegalArgumentException() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Task number is incorrect. Max task number is: 3");

        //given
        Group group = new Group();

        Lecturer lecturer = mockLecturer();
        lecturerRepository.save(lecturer);

        Course course = mockCourse();
        course.setLecturer(lecturer);
        courseRepository.save(course);

        Set<Course> courses = new HashSet<>();
        courses.add(course);
        group.setCourses(courses);
        lecturer.setCourses(courses);
        groupRepository.save(group);

        Student student = mockStudent(group);
        studentRepository.save(student);

        Task task = mockTask(course);
        taskRepository.save(task);

        //when
        gradeManagerService.addGrade(student.getId(), task.getId(), 4, 5, lecturer.getId());
    }

    @Test
    public void whenEditGrade_givenCorrectInformation_thenUpdateGrade() {

        //given
        Group group = new Group();

        Lecturer lecturer = mockLecturer();
        lecturerRepository.save(lecturer);

        Course course = mockCourse();
        course.setLecturer(lecturer);
        courseRepository.save(course);

        Set<Course> courses = new HashSet<>();
        courses.add(course);
        group.setCourses(courses);
        lecturer.setCourses(courses);
        groupRepository.save(group);

        Student student = mockStudent(group);
        studentRepository.save(student);

        Task task = mockTask(course);
        taskRepository.save(task);

        Grade grade = mockGrade(task, TASK_NUMBER, student);
        gradeRepository.save(grade);

        //when
        gradeManagerService.editGrade(student.getId(), task.getId(), TASK_NUMBER, 4.5, lecturer.getId());

        //then
        Optional<Grade> fetchGrade = gradeRepository.findByStudentIdAndTaskIdAndTaskNumber(student.getId(), task.getId(), 0);
        assertTrue(fetchGrade .isPresent());
        assertEquals(fetchGrade.get().getGradeValue(), 4.5);
    }

    private Grade mockGrade(Task task, int taskNumber, Student student) {
        return Grade.builder()
                .task(task)
                .taskNumber(taskNumber)
                .student(student)
                .gradeValue(5.0)
                .build();
    }
}