package semato.semato_learn.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="`group`")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String faculty;

    @NonNull
    private String field;

    private String academicYear;

    @OneToMany(mappedBy = "group")
    @Builder.Default
    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy = "group")
    private Set<Course> courses;

}
