package semato.semato_learn.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
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

    private Date writeDate = new Date();
}
