package semato.semato_learn.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class ProjectGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    @NonNull
    private Task task;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "student_project_group",
            joinColumns = {@JoinColumn(name = "project_group_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")}
    )
    @NonNull
    private Set<Student> students;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();
}
