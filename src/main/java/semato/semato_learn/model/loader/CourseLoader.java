package semato.semato_learn.model.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import semato.semato_learn.model.Course;
import semato.semato_learn.model.Lecturer;
import semato.semato_learn.model.Task;
import semato.semato_learn.model.TaskType;
import semato.semato_learn.model.repository.CourseRepository;
import semato.semato_learn.model.repository.GroupRepository;
import semato.semato_learn.model.repository.TaskRepository;
import semato.semato_learn.model.repository.UserBaseRepository;

import java.util.HashSet;
import java.util.Set;

@Component
@Profile("!test")
@Order(3)
public class CourseLoader implements ApplicationRunner {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserBaseRepository<Lecturer> lecturerRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Course course = Course.builder()
                .name("Technologie Obiektowe")
                .description("HTML/CSS/JS")
                .group(groupRepository.findById(1L).orElseThrow(RuntimeException::new))
                .lecturer(lecturerRepository.findById(1L).orElseThrow(RuntimeException::new))
                .build();

        course.setTasks(createTasks(course));
        courseRepository.save(course);
    }


    private Set<Task> createTasks(Course course) {
        Set<Task> tasks = new HashSet<>();
        tasks.add(Task.builder()
                .course(course)
                .quantity(3)
                .markWeight(3)
                .taskType(TaskType.LAB)
                .build());
        tasks.add(Task.builder()
                .course(course)
                .quantity(3)
                .markWeight(3)
                .taskType(TaskType.DISCUSSIONS)
                .build());
        tasks.add(Task.builder()
                .course(course)
                .quantity(3)
                .markWeight(3)
                .maxGroupQuantity(3)
                .taskType(TaskType.PROJECT)
                .build());
        tasks.add(Task.builder()
                .course(course)
                .quantity(1)
                .markWeight(5)
                .maxGroupQuantity(0)
                .taskType(TaskType.EXAM)
                .build());
        return tasks;
    }
}
