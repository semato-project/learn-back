package semato.semato_learn.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import semato.semato_learn.SematoLearnApplication;
import semato.semato_learn.controller.payload.NewsCreateRequest;
import semato.semato_learn.controller.payload.NewsEditRequest;
import semato.semato_learn.controller.payload.NewsResponse;
import semato.semato_learn.model.*;
import semato.semato_learn.model.repository.GroupRepository;
import semato.semato_learn.model.repository.NewsRepository;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Autowired
    private GroupRepository groupRepository;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();


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

        List<NewsResponse> allNewsByLecturer1 = newsService.getAllByLecturer(lecturer1);
        assertEquals(2, allNewsByLecturer1.size());
    }

    @Test
    public void shouldReturnListOfNews_andByStudent() {
        Group group1 = new Group();
        Lecturer lecturer1 = mockService.mockLecturer();
        Course course1 = mockService.mockCourse(lecturer1);
        course1.setGroup(group1);
        groupRepository.save(group1);
        Student student1 = mockService.mockStudent(group1);
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

        Group group2 = new Group();
        Lecturer lecturer2 = mockService.mockLecturer("second@gmail.com");
        Course course2 = mockService.mockCourse(lecturer2);
        course2.setGroup(group2);
        groupRepository.save(group2);
        Student student2 = mockService.mockStudent(group2, "secondStudent@gmail.com");
        newsRepository.save(News.builder()
                .title("News innego wykladowcy")
                .description("Bla bla bla")
                .lecturer(lecturer2)
                .build());

        List<NewsResponse> student1News = newsService.getAllByStudentGroup(student1);
        List<NewsResponse> student2News = newsService.getAllByStudentGroup(student2);
        assertEquals(2, student1News.size());
        assertEquals(1, student2News.size());
    }

    @Test
    public void whenGetByIdAndLecturer_givenNonExistsNewsId_thenThrowsIllegalArgumentException() {
        //given
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("News not found!");

        Lecturer lecturer = mockService.mockLecturer();
        Long notExistsNewsId = 100L;

        //when
        newsService.getByIdAndLecturer(notExistsNewsId, lecturer);
    }

    @Test
    public void whenGetByIdAndLecturer_givenExistsNewsIdManagedByOtherLecturer_thenThrowsIllegalArgumentException() {
        //given
        Lecturer lecturer1 = mockService.mockLecturer();
        Lecturer lecturer2 = mockService.mockLecturer("other@gmail.com");

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("This is not manage by this Lecturer " + lecturer2.getId() + "!");
        News news = newsRepository.save(News.builder()
                .title("Koronawirus!")
                .description("Z powodu koronawirusa zostancie w domu!")
                .lecturer(lecturer1)
                .build());

        //when
        newsService.getByIdAndLecturer(news.getId(), lecturer2);
    }


    @Test
    public void whenAdd_givenNewsRequest_thenSaveNews() {
        //given
        Lecturer lecturer1 = mockService.mockLecturer();
        NewsCreateRequest newsCreateRequest = new NewsCreateRequest("Title", "Description");

        //when
        NewsResponse news = newsService.add(newsCreateRequest, lecturer1);

        //then
        assertTrue(newsRepository.findById(news.getId()).isPresent());
    }

    @Test
    public void whenDelete_givenNewsId_thenSetDeletedAtValue() {
        //given
        Lecturer lecturer1 = mockService.mockLecturer();
        News news = newsRepository.save(News.builder()
                .title("Koronawirus!")
                .description("Z powodu koronawirusa zostancie w domu!")
                .lecturer(lecturer1)
                .build());

        //when
        newsService.delete(news.getId(), lecturer1);

        //then
        assertTrue(newsRepository.findById(news.getId()).isPresent());
        assertNotNull(newsRepository.findById(news.getId()).get().getDeletedAt());
    }

    @Test
    public void whenEdit_givenNewsIdAndNewsRequest_thenUpdateNews() {
        //given
        Lecturer lecturer1 = mockService.mockLecturer();
        NewsEditRequest newsEditRequest = new NewsEditRequest("Title", "Description");
        News news = newsRepository.save(News.builder()
                .title("Koronawirus!")
                .description("Z powodu koronawirusa zostancie w domu!")
                .lecturer(lecturer1)
                .build());

        //when
        newsService.edit(newsEditRequest, news.getId(), lecturer1);

        //then
        assertEquals("Title", newsRepository.findById(news.getId()).get().getTitle());
    }

}