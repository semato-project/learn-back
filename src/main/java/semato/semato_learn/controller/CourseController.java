package semato.semato_learn.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import semato.semato_learn.controller.payload.CourseRequest;
import semato.semato_learn.controller.payload.ProjectGroupResponse;
import semato.semato_learn.model.ProjectGroup;
import semato.semato_learn.model.Student;
import semato.semato_learn.service.CourseService;
import semato.semato_learn.service.ProjectGroupService;
import semato.semato_learn.util.security.CurrentUser;
import semato.semato_learn.util.security.UserPrincipal;
import springfox.documentation.annotations.ApiIgnore;

import java.util.LinkedList;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ProjectGroupService projectGroupService;

    @Secured({"ROLE_LECTURER"})
    @PostMapping("/")
    @ApiOperation(value = "Adding new course")
    public ResponseEntity add(@RequestBody CourseRequest courseRequest, @ApiIgnore @CurrentUser UserPrincipal currentUser) {
        try {
            courseService.add(courseRequest, currentUser.getUser());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Secured({"ROLE_LECTURER", "ROLE_STUDENT"})
    @GetMapping("/")
    @ApiOperation(value = "Get all courses")
    public ResponseEntity getAll(@ApiIgnore @CurrentUser UserPrincipal currentUser) {
        return ResponseEntity.ok(courseService.getAll(currentUser.getUser()));
    }


    @GetMapping("/{id}")
    @Secured({"ROLE_LECTURER", "ROLE_STUDENT"})
    public ResponseEntity getExtended(@PathVariable("id") Long courseId, @ApiIgnore @CurrentUser UserPrincipal currentUser){
        return ResponseEntity.ok(courseService.getExtended(courseId, currentUser.getUser()));
    }

    @PostMapping("/{id}/project-group")
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

    @GetMapping("/{id}/project-group")
    @Secured({"ROLE_LECTURER", "ROLE_STUDENT"})
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
