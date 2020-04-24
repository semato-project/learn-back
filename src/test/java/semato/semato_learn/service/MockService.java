package semato.semato_learn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import semato.semato_learn.model.*;
import semato.semato_learn.model.repository.CourseRepository;
import semato.semato_learn.model.repository.GradeRepository;
import semato.semato_learn.model.repository.TaskRepository;
import semato.semato_learn.model.repository.UserBaseRepository;

@Service
public class MockService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserBaseRepository<Student> studentRepository;

    @Autowired
    private UserBaseRepository<Lecturer> lecturerRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private GradeRepository gradeRepository;

    Lecturer mockLecturer() {
        Lecturer lecturer = Lecturer.builder()
                .firstName("Karol")
                .lastName("Krawczyk")
                .password("tajnehaslo")
                .role(RoleName.ROLE_LECTURER)
                .email("karol.krawczyk@semato.pl")
                .build();
        lecturerRepository.save(lecturer);
        return lecturer;
    }

    Lecturer mockLecturer(String email) {
        Lecturer lecturer = Lecturer.builder()
                .firstName("Karol")
                .lastName("Krawczyk")
                .password("tajnehaslo")
                .role(RoleName.ROLE_LECTURER)
                .email(email)
                .build();
        lecturerRepository.save(lecturer);
        return lecturer;
    }

    Course mockCourse(Lecturer lecturer) {
        Course course = Course.builder()
                .name("Kurs migowego")
                .description("Wypasiony kurs migowego")
                .lecturer(lecturer)
                .build();
        courseRepository.save(course);
        return course;
    }

    Course mockCourse() {
        Course course = Course.builder()
                .name("Kurs migowego")
                .description("Wypasiony kurs migowego")
                .build();
        courseRepository.save(course);
        return course;
    }

    Student mockStudent(Group group) {
        Student student = Student.builder()
                .group(group)
                .email("mikolajek@semato.com")
                .password("tajneHaslo")
                .firstName("Mikolajek")
                .lastName("Klucznik")
                .role(RoleName.ROLE_STUDENT)
                .build();
        studentRepository.save(student);
        return student;

    }


    Task mockTask(Course course) {
        Task task = Task.builder()
                .course(course)
                .quantity(3)
                .markWage(1)
                .taskType(TaskType.LAB)
                .build();
        taskRepository.save(task);
        return task;
    }


    Grade mockGrade(Task task, int taskNumber, Student student) {
        Grade grade = Grade.builder()
                .task(task)
                .taskNumber(taskNumber)
                .student(student)
                .gradeValue(5.0)
                .build();
        gradeRepository.save(grade);
        return grade;
    }
}
