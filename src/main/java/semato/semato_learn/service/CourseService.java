package semato.semato_learn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import semato.semato_learn.controller.payload.CourseRequest;
import semato.semato_learn.controller.payload.CourseResponse;
import semato.semato_learn.controller.payload.TaskRequest;
import semato.semato_learn.model.Course;
import semato.semato_learn.model.Lecturer;
import semato.semato_learn.model.Task;
import semato.semato_learn.model.User;
import semato.semato_learn.model.repository.CourseRepository;
import semato.semato_learn.model.repository.GroupRepository;
import semato.semato_learn.model.repository.TaskRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;


    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private GroupRepository groupRepository;

    public void add(CourseRequest courseRequest, User user) {

        Course course = new Course();
        course.setLecturer((Lecturer) user);
        course.setGroup(groupRepository.findById(courseRequest.getGroupId()).get());
        course.setName(courseRequest.getName());
        course.setDescription(courseRequest.getDescription());

        courseRepository.save(course);

        for (TaskRequest taskRequest: courseRequest.getTaskList()) {
            Task task = new Task();
            task.setCourse(course);
            task.setMarkWage(taskRequest.getMarkWage());
            task.setQuantity(taskRequest.getQuantity());
            task.setMaxGroupQuantity(taskRequest.getMaxGroupQuantity());
            task.setTaskType(taskRequest.getTaskType());
            taskRepository.save(task);
        }

        courseRepository.flush();

    }

    public List<CourseResponse> getAll(Long id) {
        List<Course> courses = courseRepository.findAllByLecturerId(id).orElse(Collections.emptyList());
        return createCoursesResponse(courses);
    }

    private List<CourseResponse> createCoursesResponse(List<Course> courses) {
        return courses.stream().map(CourseResponse::create).collect(Collectors.toList());
    }
}
