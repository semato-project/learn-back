package semato.semato_learn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import semato.semato_learn.controller.payload.NewsCreateRequest;
import semato.semato_learn.controller.payload.NewsEditRequest;
import semato.semato_learn.controller.payload.NewsResponse;
import semato.semato_learn.model.Lecturer;
import semato.semato_learn.model.News;
import semato.semato_learn.model.Student;
import semato.semato_learn.model.User;
import semato.semato_learn.model.repository.NewsRepository;
import semato.semato_learn.model.repository.UserBaseRepository;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.isEmpty;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UserBaseRepository<Lecturer> lecturerBaseRepository;

    public List<NewsResponse> getAllByLecturer(User user) {
        List<News> news = newsRepository.findAllByLecturerIdAndDeletedAtIsNull(user.getId()).orElse(Collections.emptyList());
        return createNewsResponse(news);
    }

    public NewsResponse getByIdAndLecturer(Long newsId, User user) throws IllegalArgumentException {
        News news = getNews(newsId, user);
        return NewsResponse.create(news);
    }

    public List<NewsResponse> getAllByStudentGroup(User user) {
        if (user instanceof Student) {
            Student student = (Student) user;
            List<News> news = newsRepository.findAllByStudentGroup(student.getGroup().getId()).orElse(Collections.emptyList());
            return createNewsResponse(news);
        } else {
            throw new ClassCastException("User isn't instanceof Student!");
        }
    }

    public NewsResponse add(NewsCreateRequest newsCreateRequest, User user) {
        News news = newsRepository.save(News.builder()
                .lecturer(lecturerBaseRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("Lecturer not found!")))
                .title(newsCreateRequest.getTitle())
                .description(newsCreateRequest.getDescription())
                .build());
        return NewsResponse.create(news);
    }

    public NewsResponse edit(NewsEditRequest newsEditRequest, Long newsId, User user) throws IllegalArgumentException {
        News news = getNews(newsId, user);
        if(!isEmpty(newsEditRequest.getTitle())) { news.setTitle(newsEditRequest.getTitle()); }
        if(!isEmpty(newsEditRequest.getDescription())) { news.setDescription(newsEditRequest.getDescription()); }
        news.setUpdatedAt(Instant.now());
        newsRepository.save(news);
        return NewsResponse.create(news);
    }

    public void delete(Long newsId, User user) throws IllegalArgumentException {
        News news = getNews(newsId, user);
        news.setDeletedAt(Instant.now());
        newsRepository.save(news);
    }

    private News getNews(Long newsId, User user) throws IllegalArgumentException {
        News news = newsRepository.findById(newsId).orElseThrow(() -> new IllegalArgumentException("News not found!"));
        if (!news.getLecturer().getId().equals(user.getId())) {
            throw new IllegalArgumentException("This is not manage by this Lecturer " + user.getId() + "!");
        }
        return news;
    }

    private List<NewsResponse> createNewsResponse(List<News> news) {
        return news.stream().map(NewsResponse::create).collect(Collectors.toList());
    }
}
