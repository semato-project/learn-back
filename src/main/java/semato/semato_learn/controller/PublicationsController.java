package semato.semato_learn.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import semato.semato_learn.controller.payload.PublicationCreateRequest;
import semato.semato_learn.controller.payload.PublicationEditRequest;
import semato.semato_learn.controller.payload.PublicationResponse;
import semato.semato_learn.model.Publication;
import semato.semato_learn.model.RoleName;
import semato.semato_learn.service.PublicationService;
import semato.semato_learn.util.security.CurrentUser;
import semato.semato_learn.util.security.UserPrincipal;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/publication")
public class PublicationsController {

    @Autowired
    private PublicationService publicationService;

    @PostMapping("/")
    @Secured({"ROLE_LECTURER"})
    @ApiOperation(value = "Endpoint to adding publication by logged lecturer")
    public ResponseEntity addPublication(@ApiIgnore @CurrentUser UserPrincipal user,
                                         @RequestBody PublicationCreateRequest publicationCreateRequest) {
        try {
            PublicationResponse publication = publicationService.add(publicationCreateRequest, user.getUser());
            return ResponseEntity.status(HttpStatus.CREATED).body(publication);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_LECTURER"})
    @ApiOperation(value = "Endpoint to editing publication by logged lecturer")
    public ResponseEntity editPublication(@ApiIgnore @CurrentUser UserPrincipal user,
                                          @PathVariable("id") Long publicationId,
                                          @RequestBody PublicationEditRequest publicationEditRequest) {
        try {
            publicationService.edit(publicationEditRequest, publicationId, user.getUser());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_LECTURER"})
    @ApiOperation(value = "Endpoint to delete publication managed by logged lecturer")
    public ResponseEntity deletePublication(@ApiIgnore @CurrentUser UserPrincipal user,
                                            @PathVariable("id") Long publicationId) {
        try {
            publicationService.delete(publicationId, user.getUser());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/")
    @ApiOperation(value = "Endpoint to get publications by logged user")
    public ResponseEntity getAllByUser(@ApiIgnore @CurrentUser UserPrincipal user) {
        if (user.getUser().getRole().equals(RoleName.ROLE_LECTURER)) {
            return ResponseEntity.ok(publicationService.getAllByLecturer(user.getUser()));
        }
        if (user.getUser().getRole().equals(RoleName.ROLE_STUDENT)) {
            try {
                return ResponseEntity.ok(publicationService.getAllByStudentGroup(user.getUser()));
            } catch (ClassCastException ex) {
                return ResponseEntity.badRequest().body(ex.getMessage());
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_LECTURER"})
    @ApiOperation(value = "Endpoint to get publication by id")
    public ResponseEntity getByIdAndLecturer(@ApiIgnore @CurrentUser UserPrincipal user, @PathVariable("id") Long publicationId) {
        try {
            return ResponseEntity.ok(publicationService.getByIdAndLecturer(publicationId, user.getUser()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
