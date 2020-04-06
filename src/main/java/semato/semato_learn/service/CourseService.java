package semato.semato_learn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import semato.semato_learn.controller.payload.CourseRequest;
import semato.semato_learn.controller.payload.TaskRequest;
import semato.semato_learn.controller.payload.UserAddRequest;
import semato.semato_learn.exception.EmailAlreadyInUse;
import semato.semato_learn.model.*;
import semato.semato_learn.model.repository.CourseRepository;
import semato.semato_learn.model.repository.GroupRepository;
import semato.semato_learn.model.repository.UserBaseRepository;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private GroupRepository groupRepository;

    public void add(CourseRequest courseRequest, User user) {

        Course course = new Course();
        course.setLecturer((Lecturer) user);
        course.setGroup(groupRepository.findById(courseRequest.getGroupId()).get());
        course.setName(courseRequest.getName());
        course.setDescription(courseRequest.getDescription());

        for (TaskRequest taskRequest: courseRequest.getTaskList()) {
            Task task = new Task();
            task.setCourse(course);
            task.setMarkWage(taskRequest.getMarkWage());
            task.setQuantity(taskRequest.getQuantity());
            task.setMaxGroupQuantity(taskRequest.getMaxGroupQuantity());
            task.setTaskType(taskRequest.getTaskType());
        }

        courseRepository.save(course);
        courseRepository.flush();

    }
}
