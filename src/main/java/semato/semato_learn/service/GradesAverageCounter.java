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
        if(student.getGradeList() != null) {
            Set<Grade> studentGradesList = student.getGradeList().stream()
                    .filter(grade -> grade.getTask().getCourse().equals(course))
                    .filter(grade -> grade.getGradeValue() != null)
                    .collect(Collectors.toSet());

            if (studentGradesList.size() > 0) {
                Double numerator = studentGradesList.stream()
                        .mapToDouble(grade -> grade.getTask().getMarkWeight() * grade.getGradeValue())
                        .sum();

                Double weightSum = studentGradesList.stream()
                        .mapToDouble(grade -> grade.getTask().getMarkWeight())
                        .sum();

                return roundUp(numerator / weightSum);
            }
        }

        return 0.0;
    }

    private Double roundUp(double number) {
        BigDecimal bigDecimalNumber = new BigDecimal(String.valueOf(number));
        double partOfTheTotal = bigDecimalNumber.intValue();
        BigDecimal decimalPart = bigDecimalNumber.subtract(new BigDecimal(partOfTheTotal));
        if(decimalPart.doubleValue() <= 0.25) {
            return partOfTheTotal;
        } else if (decimalPart.doubleValue() > 0.25 && decimalPart.doubleValue() < 0.75) {
            return partOfTheTotal + 0.5;
        } else if (decimalPart.doubleValue() > 0.75) {
            return partOfTheTotal + 1;
        } else {
            return partOfTheTotal;
        }
    }
}
