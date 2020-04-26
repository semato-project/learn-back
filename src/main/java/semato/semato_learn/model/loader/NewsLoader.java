package semato.semato_learn.model.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import semato.semato_learn.model.Lecturer;
import semato.semato_learn.model.News;
import semato.semato_learn.model.repository.UserBaseRepository;
import semato.semato_learn.model.repository.NewsRepository;

@Component
@Profile("!test")
@Order(4)
public class NewsLoader implements ApplicationRunner {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UserBaseRepository<Lecturer> lecturerUserBaseRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        newsRepository.save(News.builder()
                .title("Koronawirus!")
                .description("Z powodu koronawirusa zostancie w domu!")
                .lecturer(lecturerUserBaseRepository.findByEmail(LecturerLoader.EMAIL).orElseThrow(RuntimeException::new))
                .build());
        newsRepository.save(News.builder()
                .title("Zajęcia odwolane!")
                .description("Zajecia odwolane do odwolania!")
                .lecturer(lecturerUserBaseRepository.findByEmail(LecturerLoader.EMAIL).orElseThrow(RuntimeException::new))
                .build());
        newsRepository.save(News.builder()
                .title("Kolko IT")
                .description("Zapraszamy na kolko IT!")
                .lecturer(lecturerUserBaseRepository.findByEmail(LecturerLoader.EMAIL).orElseThrow(RuntimeException::new))
                .build());
    }
}
