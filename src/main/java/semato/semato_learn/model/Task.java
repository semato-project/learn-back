package semato.semato_learn.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    @NonNull
    private Course course;

    @OneToMany(mappedBy = "task")
    private Set<ProjectGroup> projectGroups;

    @OneToMany(mappedBy = "task")
    private Set<Grade> grades;

    @Builder.Default
    private int quantity = 0;

    @Builder.Default
    private double markWeight = 0;

    @Builder.Default
    private int maxGroupQuantity = 0;

    @Enumerated(EnumType.STRING)
    private TaskType taskType;
}
