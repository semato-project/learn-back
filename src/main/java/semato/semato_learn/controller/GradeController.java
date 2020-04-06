package semato.semato_learn.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import semato.semato_learn.service.GradeManagerService;
import semato.semato_learn.util.security.CurrentUser;
import semato.semato_learn.util.security.UserPrincipal;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequestMapping("/api/grade")
public class GradeController {

    @Autowired
    private GradeManagerService gradeManagerService;

    @PutMapping("/add")
    @ApiOperation(value = "Endpoint to add grade for user to concrete task")
    @Secured({"ROLE_LECTURER"})
    public ResponseEntity addGrade(@ApiParam(example = "1") @RequestParam long studentId,
                                   @ApiParam(example = "1") @RequestParam long taskId,
                                   @ApiParam(example = "1") @RequestParam int taskNumber,
                                   @ApiParam(example = "4.5") @RequestParam double grade,
                                   @ApiIgnore @CurrentUser UserPrincipal currentUser) {

        try {
            gradeManagerService.addGrade(studentId, taskId, taskNumber, grade, currentUser.getId());
        } catch (IllegalArgumentException exp) {
            return ResponseEntity.badRequest().body(exp.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/edit")
    @ApiOperation(value = "Endpoint to edit grade")
    @Secured({"ROLE_LECTURER"})
    public ResponseEntity editGrade(@ApiParam(example = "1") @RequestParam long studentId,
                                    @ApiParam(example = "1") @RequestParam long taskId,
                                    @ApiParam(example = "1") @RequestParam int taskNumber,
                                    @ApiParam(example = "4.5") @RequestParam double newGrade,
                                    @ApiIgnore @CurrentUser UserPrincipal currentUser) {
        try {
            gradeManagerService.editGrade(studentId, taskId, taskNumber, newGrade, currentUser.getId());
        } catch (IllegalArgumentException exp) {
            return ResponseEntity.badRequest().body(exp.getMessage());
        }

        return ResponseEntity.ok().build();
    }
}
