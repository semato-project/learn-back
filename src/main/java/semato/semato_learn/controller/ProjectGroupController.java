package semato.semato_learn.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import semato.semato_learn.model.Student;
import semato.semato_learn.service.ProjectGroupService;
import semato.semato_learn.util.security.CurrentUser;
import semato.semato_learn.util.security.UserPrincipal;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/project-group")
public class ProjectGroupController {

    @Autowired
    private ProjectGroupService projectGroupService;

    @PutMapping("/{id}")
    @Secured({"ROLE_STUDENT"})
    @ApiOperation(value = "Join ProjectGroup for Task")
    public ResponseEntity join(@PathVariable("id") Long id, @ApiIgnore @CurrentUser UserPrincipal currentUser) {
        try {
            projectGroupService.join((Student) currentUser.getUser(), id);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

}
