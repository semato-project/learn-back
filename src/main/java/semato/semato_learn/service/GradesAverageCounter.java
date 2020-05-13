package semato.semato_learn.service;

import org.springframework.stereotype.Service;
import semato.semato_learn.model.Course;
import semato.semato_learn.model.Grade;
import semato.semato_learn.model.Student;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GradesAverageCounter {

    public Double getStudentGradeAverage(Student student, Course course) {
        if (student.getGradeList() != null) {
            Set<Grade> studentGradesList = student.getGradeList().stream()
                    .filter(grade -> grade.getTask().getCourse().getId().equals(course.getId()))
                    .filter(grade -> grade.getGradeValue() != null)
                    .collect(Collectors.toSet());

            if (studentGradesList.size() > 0) {
                Double numerator = studentGradesList.stream()
                        .mapToDouble(grade -> grade.getTask().getMarkWeight() * grade.getGradeValue())
                        .sum();

                Double weightSum = studentGradesList.stream()
                        .mapToDouble(grade -> grade.getTask().getMarkWeight())
                        .sum();
                return (weightSum > 0) ? roundUp(numerator / weightSum) : roundUp(numerator / 1);
            }
        }


        return 0.0;
    }

    private Double roundUp(double number) {
        int partOfTheTotal = (int) number;
        double decimalPart = partOfTheTotal - number;
        if (decimalPart <= 0.25) {
            return partOfTheTotal + 0.0;
        } else if (decimalPart > 0.25 && decimalPart < 0.75) {
            return partOfTheTotal + 0.5;
        } else if (decimalPart > 0.75) {
            return partOfTheTotal + 1.0;
        } else {
            return partOfTheTotal + 0.0;
        }
    }

}
