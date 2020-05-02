package semato.semato_learn.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import semato.semato_learn.controller.payload.ProjectGroupResponse;
import semato.semato_learn.model.ProjectGroup;
import semato.semato_learn.model.Student;
import semato.semato_learn.service.ProjectGroupService;
import semato.semato_learn.util.security.CurrentUser;
import semato.semato_learn.util.security.UserPrincipal;
import springfox.documentation.annotations.ApiIgnore;

import java.util.LinkedList;

@RestController
@RequestMapping("/api/project_group")
public class ProjectGroupController {

    @Autowired
    private ProjectGroupService projectGroupService;

    @PostMapping("/for_course/{id}")
    @Secured({"ROLE_STUDENT"})
    @ApiOperation(value = "Creates ProjectGroup for Course")
    public ResponseEntity create(@PathVariable("id") Long id, @ApiIgnore @CurrentUser UserPrincipal currentUser) {
        try {
            projectGroupService.create((Student) currentUser.getUser(), id);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/join/{id}")
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

    @GetMapping("/for_course/{id}")
    @Secured({"ROLE_STUDENT"})
    @ApiOperation(value = "Returns existing projectGroup list for course.")
    public ResponseEntity getByCourse(@PathVariable("id") Long id, @ApiIgnore @CurrentUser UserPrincipal currentUser) {
        try {
            LinkedList<ProjectGroupResponse> projectGroupResponseList = new LinkedList<ProjectGroupResponse>();
            for (ProjectGroup projectGroup: projectGroupService.getProjectGroupListForCourse(id)) {
                projectGroupResponseList.add(new ProjectGroupResponse(projectGroup));
            }
            return ResponseEntity.ok(projectGroupResponseList);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
