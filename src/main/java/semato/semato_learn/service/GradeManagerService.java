package semato.semato_learn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import semato.semato_learn.model.*;
import semato.semato_learn.model.repository.GradeRepository;
import semato.semato_learn.model.repository.TaskRepository;
import semato.semato_learn.model.repository.UserBaseRepository;
import java.util.LinkedList;
import java.time.Instant;

@Service
public class GradeManagerService {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserBaseRepository<Student> studentRepository;

    @Autowired
    private UserBaseRepository<Lecturer> lecturerRepository;

    @Autowired
    private TaskRepository taskRepository;

    public Grade addGrade(long studentId, long taskId, int taskNumber, double grade, Long lecturerId) throws IllegalArgumentException {

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

        return gradeRepository.save(Grade.builder()
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
        gradeToUpdate.setUpdatedAt(Instant.now());

        gradeRepository.save(gradeToUpdate);
    }

    public void updateOrCreate(Student student, Task task, int taskNumber, double gradeValue) {
        Grade grade = findOrCreate(student, task, taskNumber);
        grade.setGradeValue(gradeValue);
        gradeRepository.save(grade);
    }

    public void update(long gradeId, double gradeValue, Lecturer currentUser) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new IllegalArgumentException("Grade not found!"));

        if (! courseService.validateOwnership(currentUser, grade.getTask().getCourse())) {
            throw new RuntimeException(String.format("Lecturer id: %d is not owner of course id: %d", currentUser.getId(), grade.getTask().getCourse().getId()));
        }
        grade.setGradeValue(gradeValue);
        gradeRepository.save(grade);
    }

    public LinkedList<Grade> getStudentGradesForCourse(Student student, Course course) {
        LinkedList<Grade> gradeList = new LinkedList<Grade>();
        for (Task task: course.getTasks()) {
            gradeList.addAll(getStudentGradesForTask(student, task));
        }
        return gradeList;
    }

    public LinkedList<Grade> getStudentGradesForTask(Student student, Task task) {
        LinkedList<Grade> gradeList = new LinkedList<Grade>();
        for (int taskNumber = 1; taskNumber <= task.getQuantity(); taskNumber++ ) {
            gradeList.add(findOrCreate(student, task, taskNumber));
        }
        return gradeList;
    }

    public Grade findOrCreate(Student student, Task task, int taskNumber) {
        if (! courseService.validateMembership(student, task.getCourse())) {
            throw new RuntimeException(String.format("Student id: %d is not member of course id: %d", student.getId(), task.getCourse().getId()));
        }

        if(taskNumber < 1 || task.getQuantity() < taskNumber){
            throw new IllegalArgumentException("Task number is incorrect. Max task number is: " + task.getQuantity());
        }
        Grade grade = gradeRepository.findByStudentIdAndTaskIdAndTaskNumber(student.getId(), task.getId(), taskNumber)
                .orElse(null);
        if (grade == null) {
            grade = Grade.builder()
                    .student(student)
                    .task(task)
                    .taskNumber(taskNumber)
                    .build();
            gradeRepository.save(grade);
        }
        return grade;
    }






}
