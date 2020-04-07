package semato.semato_learn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import semato.semato_learn.controller.payload.CourseRequest;
import semato.semato_learn.service.CourseService;
import semato.semato_learn.util.security.CurrentUser;
import semato.semato_learn.util.security.UserPrincipal;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Secured({"ROLE_LECTURER"})
    @PostMapping("/")
    public ResponseEntity add(@RequestBody CourseRequest courseRequest, @ApiIgnore @CurrentUser UserPrincipal currentUser) {
        try {
            courseService.add(courseRequest, currentUser.getUser());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}