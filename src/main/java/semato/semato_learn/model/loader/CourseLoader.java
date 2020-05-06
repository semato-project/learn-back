package semato.semato_learn.model.loader;

import lombok.Builder;
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
import semato.semato_learn.model.repository.UserBaseRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static semato.semato_learn.model.loader.LecturerLoader.PROFESORDOKTOR_EMAIL;
import static semato.semato_learn.model.loader.LecturerLoader.DRSTRANGE_EMAIL;

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
                .lecturer(lecturerRepository.findByEmail(PROFESORDOKTOR_EMAIL).orElseThrow(RuntimeException::new))
                .build();

        Course course2 = Course.builder()
                .name("Programowanie obiektowe")
                .description("Wprowadzenie do języka JAVA")
                .group(groupRepository.findById(1L).orElseThrow(RuntimeException::new))
                .lecturer(lecturerRepository.findByEmail(DRSTRANGE_EMAIL).orElseThrow(RuntimeException::new))
                .build();

        Course course3 = Course.builder()
                .name("Inzynieria programowania")
                .description("Nauka o UML")
                .group(groupRepository.findById(2L).orElseThrow(RuntimeException::new))
                .lecturer(lecturerRepository.findByEmail(PROFESORDOKTOR_EMAIL).orElseThrow(RuntimeException::new))
                .build();

        Course course4 = Course.builder()
                .name("Sztuczna inteligencja")
                .description("Hit czy kit?")
                .group(groupRepository.findById(3L).orElseThrow(RuntimeException::new))
                .lecturer(lecturerRepository.findByEmail(DRSTRANGE_EMAIL).orElseThrow(RuntimeException::new))
                .build();

        Set<Task> taskList1 = new HashSet<>();
        taskList1.add(generateTask(course, 3, 3, TaskType.LAB));
        taskList1.add(generateTask(course, 3, 3, TaskType.DISCUSSIONS));
        taskList1.add(generateTask(course, 1, 5, TaskType.EXAM));
        taskList1.add(generateTask(course, 1, 3, 3, TaskType.PROJECT));
        course.setTasks(taskList1);


        Set<Task> taskList2 = new HashSet<>();
        taskList2.add(generateTask(course, 2, 2, TaskType.LAB));
        taskList2.add(generateTask(course, 2, 2, TaskType.DISCUSSIONS));
        taskList2.add(generateTask(course, 1, 5, TaskType.EXAM));
        taskList2.add(generateTask(course, 1, 3, 2, TaskType.PROJECT));
        course2.setTasks(taskList2);

        Set<Task> taskList3 = new HashSet<>();
        taskList3.add(generateTask(course, 4, 1, TaskType.DISCUSSIONS));
        taskList3.add(generateTask(course, 1, 4, TaskType.EXAM));
        course3.setTasks(taskList3);

        Set<Task> taskList4 = new HashSet<>();
        taskList4.add(generateTask(course, 3, 3, TaskType.LAB));
        taskList4.add(generateTask(course, 3, 3, TaskType.DISCUSSIONS));
        course4.setTasks(taskList4);
        courseRepository.save(course);
        courseRepository.save(course2);
        courseRepository.save(course3);
        courseRepository.save(course4);
    }

    private Task generateTask(Course course, int quantity, int markWeight, int maxGroupQuantity, TaskType taskType) {
        return Task.builder()
                .course(course)
                .quantity(quantity)
                .markWeight(markWeight)
                .maxGroupQuantity(maxGroupQuantity)
                .taskType(taskType)
                .build();
    }

    private Task generateTask(Course course, int quantity, int markWeight, TaskType taskType) {
        return generateTask(course, quantity, markWeight, 0, taskType);
    }
}
