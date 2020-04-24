package semato.semato_learn.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import semato.semato_learn.SematoLearnApplication;
import semato.semato_learn.controller.payload.NewsRequest;
import semato.semato_learn.controller.payload.NewsResponse;
import semato.semato_learn.model.Lecturer;
import semato.semato_learn.model.News;
import semato.semato_learn.model.repository.NewsRepository;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SematoLearnApplication.class)
@Transactional
public class NewsServiceTest {

    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private MockService mockService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void shouldReturnEmptyList() {
        List<NewsResponse> allGroups = newsService.getAll();
        assertTrue(allGroups.isEmpty());
    }

    @Test
    public void shouldReturnListOfAllNews_andForByLecturer() {
        Lecturer lecturer1 = mockService.mockLecturer();
        Lecturer lecturer2 = mockService.mockLecturer("lecturer2@gmail.com");
        newsRepository.save(News.builder()
                .title("Koronawirus!")
                .description("Z powodu koronawirusa zostancie w domu!")
                .lecturer(lecturer1)
                .build());
        newsRepository.save(News.builder()
                .title("Zajęcia odwolane!")
                .description("Zajecia odwolane do odwolania!")
                .lecturer(lecturer1)
                .build());
        newsRepository.save(News.builder()
                .title("News innego wykladowcy")
                .description("Bla bla bla")
                .lecturer(lecturer2)
                .build());

        List<NewsResponse> allNews = newsService.getAll();
        List<NewsResponse> allNewsByLecturer1 = newsService.getAllByLecturer(lecturer1);
        assertEquals(3, allNews.size());
        assertEquals(2, allNewsByLecturer1.size());
    }

    @Test
    public void whenGetById_givenNonExistsNewsId_thenThrowsIllegalArgumentException(){
        //given
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("News not found!");

        Long notExistsNewsId = 100L;

        //when
        newsService.getById(notExistsNewsId);
    }

    @Test
    public void whenGetOneById_givenNonExistsNewsId_thenThrowsIllegalArgumentException(){
        //given
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("News not found!");

        Lecturer lecturer = mockService.mockLecturer();
        Long notExistsNewsId = 100L;

        //when
        newsService.getOneById(notExistsNewsId, lecturer);
    }

    @Test
    public void whenGetOneById_givenExistsNewsIdManagedByOtherLecturer_thenThrowsIllegalArgumentException(){
        //given
        Lecturer lecturer1 = mockService.mockLecturer();
        Lecturer lecturer2 = mockService.mockLecturer("other@gmail.com");

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("This is not manage by this Lecturer "+ lecturer2.getId()+"!");
        News news = newsRepository.save(News.builder()
                .title("Koronawirus!")
                .description("Z powodu koronawirusa zostancie w domu!")
                .lecturer(lecturer1)
                .build());

        //when
        newsService.getOneById(news.getId(), lecturer2);
    }


    @Test
    public void whenAdd_givenNewsRequest_thenSave(){
       //given
        Lecturer lecturer1 = mockService.mockLecturer();
        NewsRequest newsRequest = new NewsRequest(lecturer1.getId(), "Title", "Description");

        //when
        News news = newsService.add(newsRequest);

        //then
        assertTrue(newsRepository.findById(news.getId()).isPresent());
    }

}