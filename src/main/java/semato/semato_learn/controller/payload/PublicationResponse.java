package semato.semato_learn.controller.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import semato.semato_learn.model.Publication;

import java.time.Instant;

@Getter
@AllArgsConstructor
@Builder
public class PublicationResponse {

    private Long id;

    private Long lecturerId;

    private String title;

    private String description;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;


    public static PublicationResponse create(Publication publication) {
        return PublicationResponse.builder()
                .id(publication.getId())
                .lecturerId(publication.getLecturer().getId())
                .title(publication.getTitle())
                .description(publication.getDescription())
                .createdAt(publication.getCreatedAt())
                .updatedAt(publication.getUpdatedAt())
                .deletedAt(publication.getDeletedAt())
                .build();
    }
}
