package semato.semato_learn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import semato.semato_learn.model.*;
import semato.semato_learn.model.repository.*;
import java.util.HashSet;
import java.util.Set;

@Service
public class ProjectGroupService {

    @Autowired
    ProjectGroupRepository projectGroupRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseService courseService;

    public void create(Student student, Long courseId) {
        Course course = courseRepository.getOne(courseId);
        if (course == null) {
            throw new RuntimeException(String.format("There is no course with id: %d.", courseId));
        }
        if (! courseService.validateMembership(student, course)) {
            throw new RuntimeException(String.format("Student %d in not member of course %d.", student.getId(), courseId));
        }
        Task project = getProjectForCourse(course);
        if (checkIfAlreadyAssigned(student, project)) {
            throw new RuntimeException(String.format("Student %d is already present in project group for course %d.", student.getId(), courseId));
        }
        HashSet<Student> studentSet = new HashSet<>();
        studentSet.add(student);
        projectGroupRepository.save(ProjectGroup.builder().task(project).students(studentSet).build());
    }

    private Task getProjectForCourse(Course course) {
        Task project = null;
        for (Task task: course.getTasks()) {
            if(task.getTaskType() == TaskType.PROJECT) {
                project = task;
                break;
            }
        }
        if (project == null) {
            throw new RuntimeException(String.format("Course %d has no project task.", course.getId()));
        }
        return project;
    }

    public void join(Student student, Long projectGroupId) {

        ProjectGroup projectGroup = projectGroupRepository.getOne(projectGroupId);
        if (projectGroup == null) {
            throw new RuntimeException(String.format("There is no projectGroup with id: %d.", projectGroupId));
        }
        Task project = projectGroup.getTask();
        Course course = project.getCourse();
        if (! courseService.validateMembership(student, course)) {
            throw new RuntimeException(String.format("Student %d in not member of course %d.", student.getId(), course.getId()));
        }
        if (checkIfAlreadyAssigned(student, project)) {
            throw new RuntimeException(String.format("Student %d is already present in project group for course %d.", student.getId(), course.getId()));
        }
        if (projectGroup.getStudents().size() >= project.getMaxGroupQuantity()) {
            throw new RuntimeException(String.format("projectGroup id: %d has no room for another student.", projectGroupId));
        }
        Set<Student> studentSet = projectGroup.getStudents();
        studentSet.add(student);
        projectGroup.setStudents(studentSet);
        projectGroupRepository.save(projectGroup);
    }

    public Set<ProjectGroup> getProjectGroupListForCourse(Long courseId) {
        Course course = courseRepository.getOne(courseId);
        if (course == null) {
            throw new RuntimeException(String.format("There is no course with id: %d.", courseId));
        }
        Task project = getProjectForCourse(course);
        return project.getProjectGroups();
    }

    private boolean checkIfAlreadyAssigned(Student student, Task task) {
        return projectGroupRepository.findByStudentsAndTask(student, task).isPresent();
    }

}
