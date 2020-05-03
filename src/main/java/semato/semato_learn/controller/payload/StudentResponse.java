package semato.semato_learn.controller.payload;

import lombok.Getter;
import semato.semato_learn.model.ProjectGroup;
import semato.semato_learn.model.Student;

import java.util.LinkedList;

@Getter
public class StudentResponse {

    private long id;
    private String email;
    private String firstName;
    private String lastName;

    public StudentResponse(Student student) {
        id = student.getId();
        email = student.getEmail();
        firstName = student.getFirstName();
        lastName = student.getLastName();
    }
}
