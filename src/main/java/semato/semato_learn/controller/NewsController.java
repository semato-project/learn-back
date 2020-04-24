package semato.semato_learn.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import semato.semato_learn.controller.payload.NewsRequest;
import semato.semato_learn.service.NewsService;
import semato.semato_learn.util.security.CurrentUser;
import semato.semato_learn.util.security.UserPrincipal;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/news/")
public class NewsController {

    @Autowired
    NewsService newsService;

    @PutMapping("/")
    @Secured({"ROLE_LECTURER"})
    @ApiOperation(value = "Endpoint to adding news by logged lecturer")
    public ResponseEntity addNews(@ApiIgnore @CurrentUser UserPrincipal user,
    @RequestBody NewsRequest newsRequest) {
        try {
            newsService.add(newsRequest);
            return ResponseEntity.ok(HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/lecturer")
    @Secured({"ROLE_LECTURER"})
    @ApiOperation(value = "Endpoint to get all news managed by logged Lecturer")
    public ResponseEntity getAllByLecturer(@ApiIgnore @CurrentUser UserPrincipal user) {
        try {
            return ResponseEntity.ok(newsService.getAllByLecturer(user.getUser()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}/lecturer")
    @Secured({"ROLE_LECTURER"})
    @ApiOperation(value = "Endpoint to get news managed by logged Lecturer and by news id")
        public ResponseEntity getByIdAndLecturer(@ApiIgnore @CurrentUser UserPrincipal user, @PathVariable("id") Long newsId) {
        try {
            return ResponseEntity.ok(newsService.getOneById(newsId, user.getUser()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/")
    @ApiOperation(value = "Endpoint to get all news")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(newsService.getAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Endpoint to get news by id")
    public ResponseEntity getById(@PathVariable("id") Long newsId) {
        try {
            return ResponseEntity.ok(newsService.getById(newsId));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
