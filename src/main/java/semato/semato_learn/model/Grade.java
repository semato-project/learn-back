package semato.semato_learn.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt"},
        allowGetters = true
)
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "student_id", nullable = false)
    @NonNull
    private Student student;

    @ManyToOne
    @JoinColumn (name = "task_id", nullable = false)
    @NonNull
    private Task task;

    private Integer taskNumber;

    @NonNull
    private Double gradeValue;

    @CreatedDate
    @Builder.Default
    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @LastModifiedDate
    @Builder.Default
    @Column(nullable = false)
    private Instant updatedAt = Instant.now();
}
