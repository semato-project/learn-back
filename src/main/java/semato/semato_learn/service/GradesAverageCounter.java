package semato.semato_learn.service;

import org.springframework.stereotype.Service;
import semato.semato_learn.model.Course;
import semato.semato_learn.model.Grade;
import semato.semato_learn.model.Student;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GradesAverageCounter {

    public Double getStudentGradeAverage(Student student, Course course) {
        if(student.getGradeList() != null) {
            Set<Grade> studentGradesList = student.getGradeList().stream()
                    .filter(grade -> grade.getTask().getCourse().equals(course))
                    .collect(Collectors.toSet());

            Double numerator = studentGradesList.stream()
                    .mapToDouble(grade -> grade.getTask().getMarkWeight() * grade.getGradeValue())
                    .sum();

            Double weightSum = studentGradesList.stream()
                    .mapToDouble(grade -> grade.getTask().getMarkWeight())
                    .sum();

            return numerator / weightSum;
        }

        return 0.0;
    }
}
