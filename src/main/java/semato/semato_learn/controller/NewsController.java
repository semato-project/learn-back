package semato.semato_learn.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import semato.semato_learn.controller.payload.NewsCreateRequest;
import semato.semato_learn.controller.payload.NewsEditRequest;
import semato.semato_learn.controller.payload.NewsResponse;
import semato.semato_learn.model.News;
import semato.semato_learn.model.RoleName;
import semato.semato_learn.service.NewsService;
import semato.semato_learn.util.security.CurrentUser;
import semato.semato_learn.util.security.UserPrincipal;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping("/")
    @Secured({"ROLE_LECTURER"})
    @ApiOperation(value = "Endpoint to adding news by logged lecturer")
    public ResponseEntity addNews(@ApiIgnore @CurrentUser UserPrincipal user,
                                  @RequestBody NewsCreateRequest newsCreateRequest) {
        try {
            NewsResponse news = newsService.add(newsCreateRequest, user.getUser());
            return ResponseEntity.status(HttpStatus.CREATED).body(news);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_LECTURER"})
    @ApiOperation(value = "Endpoint to editing news by logged lecturer")
    public ResponseEntity editNews(@ApiIgnore @CurrentUser UserPrincipal user,
                                   @PathVariable("id") Long newsId,
                                   @RequestBody NewsEditRequest newsEditRequest) {
        try {
            newsService.edit(newsEditRequest, newsId, user.getUser());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_LECTURER"})
    @ApiOperation(value = "Endpoint to delete news managed by logged lecturer")
    public ResponseEntity deleteNews(@ApiIgnore @CurrentUser UserPrincipal user,
                                     @PathVariable("id") Long newsId) {
        try {
            newsService.delete(newsId, user.getUser());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/")
    @ApiOperation(value = "Endpoint to get news by logged user")
    public ResponseEntity getAllByUser(@ApiIgnore @CurrentUser UserPrincipal user) {
        if (user.getUser().getRole().equals(RoleName.ROLE_LECTURER)) {
            return ResponseEntity.ok(newsService.getAllByLecturer(user.getUser()));
        }
        if (user.getUser().getRole().equals(RoleName.ROLE_STUDENT)) {
            try {
                return ResponseEntity.ok(newsService.getAllByStudentGroup(user.getUser()));
            } catch (ClassCastException ex) {
                return ResponseEntity.badRequest().body(ex.getMessage());
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_LECTURER"})
    @ApiOperation(value = "Endpoint to get news by id")
    public ResponseEntity getByIdAndLecturer(@ApiIgnore @CurrentUser UserPrincipal user, @PathVariable("id") Long newsId) {
        try {
            return ResponseEntity.ok(newsService.getByIdAndLecturer(newsId, user.getUser()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
