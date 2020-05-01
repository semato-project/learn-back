package semato.semato_learn.service;

import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import semato.semato_learn.controller.payload.CourseRequest;
import semato.semato_learn.controller.payload.CourseResponse;
import semato.semato_learn.controller.payload.CourseExtendedResponse;
import semato.semato_learn.controller.payload.TaskRequest;
import semato.semato_learn.exception.InvalidGranAuthority;
import semato.semato_learn.model.*;
import semato.semato_learn.model.repository.CourseRepository;
import semato.semato_learn.model.repository.GroupRepository;
import semato.semato_learn.model.repository.TaskRepository;

import javax.persistence.PostUpdate;
import javax.persistence.PreUpdate;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GradeManagerService gradeManagerService;

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
            task.setMarkWeight(taskRequest.getMarkWeight());
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

    public CourseExtendedResponse getExtended(Long courseId, User user) {

        Course course = courseRepository.getOne(courseId);
        Set<Student> studentList = new HashSet<>();

        if (user.getRole() == RoleName.ROLE_LECTURER) {
            if (! validateOwnership((Lecturer) user, course)) {
                throw new RuntimeException(String.format("Lecturer id: %d is not owner of course id: %d", user.getId(), course));
            }
            studentList = course.getGroup().getStudents();
        } else if (user.getRole() == RoleName.ROLE_STUDENT) {
            if (! validateMembership((Student) user, course)) {
                throw new RuntimeException(String.format("Student id: %d is not member of course id: %d", user.getId(), course.getId()));
            }
            studentList.add((Student) user);

        } else {
            throw new InvalidGranAuthority();
        }
        CourseExtendedResponse courseExtendedResponse = new CourseExtendedResponse(course, studentList, gradeManagerService);
        return courseExtendedResponse;
    }

    public boolean validateMembership(Student student, Course course) {
        return student.getGroup().getId() == course.getGroup().getId();
    }


    public boolean validateOwnership(Lecturer lecturer, Course course) {
        return course.getLecturer().getId() == lecturer.getId();
    }

    @PreUpdate
    public void onGradePreUpdate(Grade grade) {
            Course course = grade.getTask().getCourse();
            course.setUpdatedAt(Instant.now());
    }

}
