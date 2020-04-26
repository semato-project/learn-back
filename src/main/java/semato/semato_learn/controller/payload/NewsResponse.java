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

    private Long lecturerId;

    private String title;

    private String description;

    private Instant createdAt;

    private Instant updatedAt;

    public static NewsResponse create(News news) {
        return NewsResponse.builder()
                .id(news.getId())
                .lecturerId(news.getLecturer().getId())
                .title(news.getTitle())
                .description(news.getDescription())
                .createdAt(news.getCreatedAt())
                .updatedAt(news.getUpdatedAt())
                .build();
    }
}
