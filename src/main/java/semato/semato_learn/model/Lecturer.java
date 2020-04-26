package semato.semato_learn.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@JsonIgnoreProperties(
        value = {"courses", "news", "publications"},
        allowGetters = true
)
public class Lecturer extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "lecturer")
    private Set<Course> courses;

    @OneToMany(mappedBy = "lecturer")
    private Set<News> news;

    @OneToMany(mappedBy = "lecturer")
    private Set<Publication> publications;

}
