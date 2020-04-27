package semato.semato_learn.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import semato.semato_learn.controller.payload.GradeRequest;
import semato.semato_learn.model.Lecturer;
import semato.semato_learn.service.GradeManagerService;
import semato.semato_learn.util.security.CurrentUser;
import semato.semato_learn.util.security.UserPrincipal;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequestMapping("/api/grade")
public class GradeController {

    @Autowired
    private GradeManagerService gradeManagerService;

    @PutMapping("/")
    @ApiOperation(value = "Endpoint to edit grade")
    @Secured({"ROLE_LECTURER"})
    public ResponseEntity editGrade(@RequestBody GradeRequest gradeRequest,
                                    @ApiIgnore @CurrentUser UserPrincipal currentUser) {
        try {
            gradeManagerService.update(gradeRequest.getId(), gradeRequest.getGrade(), (Lecturer) currentUser.getUser());
        } catch (IllegalArgumentException exp) {
            return ResponseEntity.badRequest().body(exp.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}
