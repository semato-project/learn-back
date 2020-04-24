package semato.semato_learn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import semato.semato_learn.controller.payload.NewsRequest;
import semato.semato_learn.controller.payload.NewsResponse;
import semato.semato_learn.model.Lecturer;
import semato.semato_learn.model.News;
import semato.semato_learn.model.User;
import semato.semato_learn.model.repository.NewsRepository;
import semato.semato_learn.model.repository.UserBaseRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UserBaseRepository<Lecturer> lecturerBaseRepository;

    public List<NewsResponse> getAllByLecturer(User user) {
        List<News> news = newsRepository.findAllByLecturerId(user.getId()).orElse(Collections.emptyList());
        return createNewsResponse(news);
    }

    public NewsResponse getOneById(Long newsId, User user) throws IllegalArgumentException {
        News news = newsRepository.findById(newsId).orElseThrow(() -> new IllegalArgumentException("News not found!"));
        if (!news.getLecturer().getId().equals(user.getId())) {
            throw new IllegalArgumentException("This is not manage by this Lecturer " + user.getId() + "!");
        }
        return NewsResponse.create(news);
    }

    public NewsResponse getById(Long newsId) {
        News news = newsRepository.findById(newsId).orElseThrow(() -> new IllegalArgumentException("News not found!"));
        return NewsResponse.create(news);
    }

    public List<NewsResponse> getAll() {
        return createNewsResponse(newsRepository.findAll());
    }

    public News add(NewsRequest newsRequest) {
        return newsRepository.save(News.builder()
                .lecturer(lecturerBaseRepository.findById(newsRequest.getLecturerId()).orElseThrow(() -> new IllegalArgumentException("Lecturer not found!")))
                .title(newsRequest.getTitle())
                .description(newsRequest.getDescription())
                .build());
    }

    private List<NewsResponse> createNewsResponse(List<News> news) {
        return news.stream().map(NewsResponse::create).collect(Collectors.toList());
    }
}
