package semato.semato_learn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import semato.semato_learn.model.Grade;
import semato.semato_learn.model.Lecturer;
import semato.semato_learn.model.Student;
import semato.semato_learn.model.Task;
import semato.semato_learn.model.repository.GradeRepository;
import semato.semato_learn.model.repository.TaskRepository;
import semato.semato_learn.model.repository.UserBaseRepository;

import java.util.Date;

@Service
public class GradeManagerService {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private UserBaseRepository<Student> studentRepository;

    @Autowired
    private UserBaseRepository<Lecturer> lecturerRepository;

    @Autowired
    private TaskRepository taskRepository;

    public void addGrade(long studentId, long taskId, int taskNumber, double grade, Long lecturerId) throws IllegalArgumentException {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found!"));

        lecturerRepository.findByCoursesAndId(task.getCourse(), lecturerId)
                .orElseThrow(() -> new IllegalArgumentException("Task isn't manage by this lecturer!"));

        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student not found!"));

        if (gradeRepository.findByStudentIdAndTaskIdAndTaskNumber(studentId, taskId, taskNumber).isPresent()) {
            throw new IllegalArgumentException("This student actually have the grade for this task number!");
        }

        if(taskNumber < 0 || task.getQuantity() < taskNumber){
            throw new IllegalArgumentException("Task number is incorrect. Max task number is: " + task.getQuantity());
        }

        gradeRepository.save(Grade.builder()
                .gradeValue(grade)
                .student(student)
                .task(task)
                .taskNumber(taskNumber)
                .build());
    }

    public void editGrade(long studentId, long taskId, int taskNumber, double grade, Long lecturerId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found!"));
        lecturerRepository.findByCoursesAndId(task.getCourse(), lecturerId)
                .orElseThrow(() -> new IllegalArgumentException("Task isn't manage by this lecturer!"));

        Grade gradeToUpdate = gradeRepository.findByStudentIdAndTaskIdAndTaskNumber(studentId, taskId, taskNumber)
                .orElseThrow(() -> new IllegalArgumentException("Grade not found!"));

        gradeToUpdate.setGradeValue(grade);
        gradeToUpdate.setWriteDate(new Date());

        gradeRepository.save(gradeToUpdate);
    }
}
