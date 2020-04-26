package semato.semato_learn.controller.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import semato.semato_learn.model.Course;
import semato.semato_learn.model.Group;
import semato.semato_learn.model.Student;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GroupResponse {

    private Long id;

    private String faculty;

    private String field;

    private String academicYear;

    private Set<Long> studentIds;

    private Set<Long> courseIds;

    private int studentQuantity;

    public static GroupResponse create(Group group) {

        Set<Long> studentIdsTmp = Optional.ofNullable(group.getStudents()).orElseGet(Collections::emptySet).stream().map(Student::getId).collect(Collectors.toSet());

        return GroupResponse.builder()
                .id(group.getId())
                .faculty(group.getFaculty())
                .field(group.getField())
                .academicYear(group.getAcademicYear())
                .studentIds(studentIdsTmp)
                .studentQuantity(studentIdsTmp.size())
                .courseIds(Optional.ofNullable(group.getCourses()).orElseGet(Collections::emptySet).stream().map(Course::getId).collect(Collectors.toSet()))
                .build();
    }
}
