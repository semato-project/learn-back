package semato.semato_learn.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import semato.semato_learn.controller.payload.UserAddRequest;
import semato.semato_learn.exception.EmailAlreadyInUse;
import semato.semato_learn.service.CreateLecturerService;
import semato.semato_learn.service.CreateStudentService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private CreateStudentService createStudentService;

    @Autowired
    private CreateLecturerService createLecturerService;

    @PostMapping("/add/student")
    public ResponseEntity addStudent(@RequestBody UserAddRequest user) {
        try {
            createStudentService.addUser(user);
        } catch (EmailAlreadyInUse emailAlreadyInUse) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/add/lecturer")
    public ResponseEntity addLecturer(@RequestBody UserAddRequest user) {
        try {
            createLecturerService.addUser(user);
        } catch (EmailAlreadyInUse emailAlreadyInUse) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok().build();
    }

    @Secured({"ROLE_STUDENT"})
    @GetMapping("/student/test")
    @ApiOperation(value = "Authorization test for student")
    public ResponseEntity testEndpointForStudent() {
        return ResponseEntity.ok("It's working!");
    }

    @Secured({"ROLE_LECTURER"})
    @GetMapping("/lecturer/test")
    @ApiOperation(value = "Authorization test for lecturer")
    public ResponseEntity testEndpointForLecturer() {
        return ResponseEntity.ok("It's working!");
    }

}
