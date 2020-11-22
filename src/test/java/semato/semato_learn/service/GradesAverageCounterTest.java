package semato.semato_learn.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import semato.semato_learn.SematoLearnApplication;
import semato.semato_learn.model.*;
import semato.semato_learn.model.repository.GroupRepository;
import semato.semato_learn.model.repository.TaskRepository;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SematoLearnApplication.class)
@Transactional
public class GradesAverageCounterTest {

    @Autowired
    private MockService mockService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GradeManagerService gradeManagerService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private GradesAverageCounter gradeAverageCounter;

    @Test
    public void countGradesAverage() {
        Group group = new Group();
        Lecturer lecturer = mockService.mockLecturer();
        Course course = mockService.mockCourse(lecturer);
        lecturer.setCourses(Collections.singleton(course));
        course.setGroup(group);
        groupRepository.save(group);
        Student student = mockService.mockStudent(group);
        Task task1 = Task.builder()
                .course(course)
                .quantity(3)
                .markWeight(1)
                .taskType(TaskType.LAB)
                .build();
        taskRepository.save(task1);

        Task task2 = Task.builder()
                .course(course)
                .quantity(1)
                .markWeight(2)
                .taskType(TaskType.PROJECT)
                .build();
        taskRepository.save(task2);

        Set<Grade> gradesTask = new HashSet<>();
        gradesTask.add(gradeManagerService.addGrade(student.getId(), task1.getId(), 1, 4, lecturer.getId()));
        gradesTask.add(gradeManagerService.addGrade(student.getId(), task1.getId(), 2, 4, lecturer.getId()));
        gradesTask.add(gradeManagerService.addGrade(student.getId(), task1.getId(), 3, 4, lecturer.getId()));
        gradesTask.add(gradeManagerService.addGrade(student.getId(), task2.getId(), 1, 5, lecturer.getId()));

        student.setGradeList(gradesTask);
        assertEquals(4, gradeAverageCounter.getStudentGradeAverage(student, course));
    }

    @Test
    public void countGradesAverage_ifGradesListIsEmpty() {
        Group group = new Group();
        Lecturer lecturer = mockService.mockLecturer();
        Course course = mockService.mockCourse(lecturer);
        lecturer.setCourses(Collections.singleton(course));
        course.setGroup(group);
        groupRepository.save(group);
        Student student = mockService.mockStudent(group);
        Task task1 = Task.builder()
                .course(course)
                .quantity(3)
                .markWeight(1)
                .taskType(TaskType.LAB)
                .build();
        taskRepository.save(task1);

        Task task2 = Task.builder()
                .course(course)
                .quantity(1)
                .markWeight(2)
                .taskType(TaskType.PROJECT)
                .build();
        taskRepository.save(task2);

        assertEquals(0.0, gradeAverageCounter.getStudentGradeAverage(student, course));
    }

    @Test
    public void countGradesAverage_ifFewCoursesExists() {
        Group group = new Group();
        Lecturer lecturer = mockService.mockLecturer();
        Course course1 = mockService.mockCourse(lecturer);
        lecturer.setCourses(Collections.singleton(course1));
        course1.setGroup(group);
        Course course2 = mockService.mockCourse(lecturer);
        lecturer.setCourses(Collections.singleton(course2));
        course2.setGroup(group);
        groupRepository.save(group);
        Student student = mockService.mockStudent(group);

        Task task1 = Task.builder()
                .course(course1)
                .quantity(3)
                .markWeight(1)
                .taskType(TaskType.LAB)
                .build();
        taskRepository.save(task1);

        Task task2 = Task.builder()
                .course(course1)
                .quantity(1)
                .markWeight(2)
                .taskType(TaskType.PROJECT)
                .build();
        taskRepository.save(task2);

        Task task3 = Task.builder()
                .course(course2)
                .quantity(2)
                .markWeight(2)
                .taskType(TaskType.LAB)
                .build();
        taskRepository.save(task3);

        Set<Grade> gradesTask = new HashSet<>();
        gradesTask.add(gradeManagerService.addGrade(student.getId(), task1.getId(), 1, 4, lecturer.getId()));
        gradesTask.add(gradeManagerService.addGrade(student.getId(), task1.getId(), 2, 4, lecturer.getId()));
        gradesTask.add(gradeManagerService.addGrade(student.getId(), task1.getId(), 3, 4, lecturer.getId()));
        gradesTask.add(gradeManagerService.addGrade(student.getId(), task2.getId(), 1, 5, lecturer.getId()));
        gradesTask.add(gradeManagerService.addGrade(student.getId(), task3.getId(), 1, 5, lecturer.getId()));
        gradesTask.add(gradeManagerService.addGrade(student.getId(), task3.getId(), 2, 5, lecturer.getId()));

        student.setGradeList(gradesTask);
        assertEquals(4, gradeAverageCounter.getStudentGradeAverage(student, course1));
    }
}