package semato.semato_learn.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
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

    private int quantity = 0;

    private double markWage = 0;

    private int maxGroupQuantity = 0;

    @Enumerated(EnumType.STRING)
    private TaskType taskType;
}
