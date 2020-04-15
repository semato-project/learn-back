package semato.semato_learn.controller.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CoursesResponse {
    private List<CourseResponse> courseList;
}
