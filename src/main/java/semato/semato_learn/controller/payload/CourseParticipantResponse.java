package semato.semato_learn.controller.payload;

import semato.semato_learn.model.Course;
import semato.semato_learn.model.Grade;
import semato.semato_learn.model.Student;

import semato.semato_learn.service.GradeManagerService;

import java.util.LinkedList;

public class CourseParticipantResponse {

    private long studentId;
    private String firstName;
    private String lastName;
    private double finalGrade;
    private LinkedList<GradeResponse> gradeList = new LinkedList<>();

    public CourseParticipantResponse (Student student, Course course, GradeManagerService gradeManagerService) {

        firstName = student.getFirstName();
        lastName = student.getLastName();
        studentId = student.getId();

        for (Grade grade: gradeManagerService.getStudentGradesForCourse(student, course)) {
            gradeList.add(new GradeResponse(grade));
        }

        finalGrade = gradeManagerService.getFinalGrade(student, course);
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

    public Double getFinalGrade() {return finalGrade; }

    public LinkedList<GradeResponse> getGradeList() {
        return gradeList;
    }
}
