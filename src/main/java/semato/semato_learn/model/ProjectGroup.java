package semato.semato_learn.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
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
}
