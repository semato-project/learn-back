package semato.semato_learn.controller.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import semato.semato_learn.model.Course;
import semato.semato_learn.model.Grade;
import semato.semato_learn.model.Student;

import semato.semato_learn.service.GradeManagerService;

import java.util.LinkedList;

public class CourseParticipantResponse {

    private long studentId;
    private String firstName;
    private String lastName;
    private LinkedList<GradeResponse> gradeList = new LinkedList<>();

    @Autowired
    private GradeManagerService gradeManagerService;

    public CourseParticipantResponse (Student student, Course course) {
        firstName = student.getFirstName();
        lastName = student.getLastName();
        studentId = student.getId();

        for (Grade grade: gradeManagerService.getStudentGradesForCourse(student, course)) {
            gradeList.add(new GradeResponse(grade));
        }
    }

    public long getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LinkedList<GradeResponse> getGradeList() {
        return gradeList;
    }
}
