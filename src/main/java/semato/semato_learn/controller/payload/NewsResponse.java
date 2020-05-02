package semato.semato_learn.controller.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import semato.semato_learn.model.News;

import java.time.Instant;

@Getter
@AllArgsConstructor
@Builder
public class NewsResponse {

    private Long id;

    private String lecturerFirstName;

    private String lecturerLastName;

    private String title;

    private String description;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;

    public static NewsResponse create(News news) {
        return NewsResponse.builder()
                .id(news.getId())
                .lecturerFirstName(news.getLecturer().getFirstName())
                .lecturerLastName(news.getLecturer().getLastName())
                .title(news.getTitle())
                .description(news.getDescription())
                .createdAt(news.getCreatedAt())
                .updatedAt(news.getUpdatedAt())
                .deletedAt(news.getDeletedAt())
                .build();
    }
}
