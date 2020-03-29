package semato.semato_learn.model;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class Lecturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @NonNull
    private User user;

    @OneToMany(mappedBy = "lecturer")
    private Set<Course> courses;

    @OneToMany(mappedBy = "lecturer")
    private Set<News> news;

    @OneToMany(mappedBy = "lecturer")
    private Set<Publication> publications;

}
