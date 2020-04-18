package semato.semato_learn.controller.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
}
