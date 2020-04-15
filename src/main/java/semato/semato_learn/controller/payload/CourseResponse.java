package semato.semato_learn.controller.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
@Builder
public class CourseResponse {
    long courseId;

    long lecturerId;

    long groupId;

    private String name;

    private String description;

    private Instant createdAt;

    private Instant updatedAt;
}
