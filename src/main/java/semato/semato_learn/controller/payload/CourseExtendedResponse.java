package semato.semato_learn.controller.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import semato.semato_learn.model.Course;
import semato.semato_learn.model.Student;

import java.util.LinkedList;
import java.util.Set;

@Getter
@NoArgsConstructor
public class CourseExtendedResponse {

    private long courseId;

    private String name;

    private LinkedList<CourseParticipantResponse> courseParticipantResponseList = new LinkedList<CourseParticipantResponse>();

    public CourseExtendedResponse(Course course, Set<Student> studentList) {
        courseId = course.getId();
        name = course.getName();
        for (Student student: studentList) {
            courseParticipantResponseList.add(new CourseParticipantResponse(student, course));
        }
    }
}
