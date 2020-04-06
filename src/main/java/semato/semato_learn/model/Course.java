package semato.semato_learn.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;

    @OneToOne
    @JoinColumn(name="group_id")
    private Group group;

    @OneToMany(mappedBy = "course")
    private Set<Task> tasks;

    @NonNull
    private String name;

    @NonNull
    private String description;

    private Date creationDate = new Date();

    private Date lastUpdate;
}
