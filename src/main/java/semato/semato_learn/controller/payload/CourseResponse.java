package semato.semato_learn.controller.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import semato.semato_learn.model.Course;

import java.time.Instant;

@Getter
@AllArgsConstructor
@Builder
public class CourseResponse {
    private long courseId;

    private long lecturerId;

    private long groupId;

    private String name;

    private String description;

    private Instant createdAt;

    private Instant updatedAt;

    public static CourseResponse create(Course course) {
        return CourseResponse.builder()
                    .courseId(course.getId())
                    .lecturerId(course.getLecturer().getId())
                    .groupId(course.getGroup().getId())
                    .name(course.getName())
                    .description(course.getDescription())
                    .courseId(course.getId())
                    .createdAt(course.getCreatedAt())
                    .updatedAt(course.getUpdatedAt())
                    .build();
    }
}
