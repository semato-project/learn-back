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
        return lecturerRepository.save(Lecturer.builder()
                .firstName("Karol")
                .lastName("Krawczyk")
                .password("tajnehaslo")
                .role(RoleName.ROLE_LECTURER)
                .email("karol.krawczyk@semato.pl")
                .build());
    }

    Lecturer mockLecturer(String email) {
        return lecturerRepository.save(Lecturer.builder()
                .firstName("Karol")
                .lastName("Krawczyk")
                .password("tajnehaslo")
                .role(RoleName.ROLE_LECTURER)
                .email(email)
                .build());
    }

    Course mockCourse(Lecturer lecturer) {
        return courseRepository.save(Course.builder()
                .name("Kurs migowego")
                .description("Wypasiony kurs migowego")
                .lecturer(lecturer)
                .build());
    }

    Course mockCourse() {
        return courseRepository.save(Course.builder()
                .name("Kurs migowego")
                .description("Wypasiony kurs migowego")
                .build());
    }

    Student mockStudent(Group group) {
        return studentRepository.save(Student.builder()
                .group(group)
                .email("mikolajek@semato.com")
                .password("tajneHaslo")
                .firstName("Mikolajek")
                .lastName("Klucznik")
                .role(RoleName.ROLE_STUDENT)
                .build());
    }

    Student mockStudent(Group group, String email) {
        return studentRepository.save(Student.builder()
                .group(group)
                .email(email)
                .password("tajneHaslo")
                .firstName("Mikolajek")
                .lastName("Klucznik")
                .role(RoleName.ROLE_STUDENT)
                .build());
    }


    Task mockTask(Course course) {
        return taskRepository.save(Task.builder()
                .course(course)
                .quantity(3)
                .markWage(1)
                .taskType(TaskType.LAB)
                .build());
    }

    Grade mockGrade(Task task, int taskNumber, Student student) {
        return gradeRepository.save(Grade.builder()
                .task(task)
                .taskNumber(taskNumber)
                .student(student)
                .gradeValue(5.0)
                .build());
    }
}
