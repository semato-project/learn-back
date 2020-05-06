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
                .description("Z powodu koronawirusa zostancie w domu!" +
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras finibus mi ut augue mollis, id suscipit quam elementum. Donec vulputate sit amet eros ac vulputate. In lorem velit, tempor nec vestibulum non, tempus ac lectus. Morbi dapibus neque eget est ullamcorper vulputate. Nam malesuada eros ut urna congue tincidunt. Suspendisse eu feugiat nulla. Sed convallis, libero a rhoncus viverra, lorem nunc efficitur justo, in vestibulum est nisi sed sapien. Nulla malesuada diam a justo ultricies viverra. Quisque nulla nunc, pretium et suscipit nec, ultricies a lacus." + System.lineSeparator() +
                        "Nulla ut risus nec nisl pharetra faucibus. Ut pretium egestas lacus. Sed vitae justo varius, pharetra lorem id, dignissim leo. Fusce interdum euismod elit, ac tempus purus ornare nec. Donec sed semper ligula. Sed enim justo, tempus ut condimentum vehicula, efficitur quis mauris. Suspendisse ultricies, tortor ac congue dignissim, elit tellus cursus enim, at faucibus turpis ligula eget mi. Fusce quis sapien et lectus ultrices imperdiet. In ac velit arcu. Curabitur vel euismod sapien.")
                .lecturer(lecturerUserBaseRepository.findByEmail(LecturerLoader.PROFESORDOKTOR_EMAIL).orElseThrow(RuntimeException::new))
                .build());
        newsRepository.save(News.builder()
                .title("Zajecia odwolane!")
                .description("Zajecia odwolane do odwolania!" +
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras finibus mi ut augue mollis, id suscipit quam elementum. Donec vulputate sit amet eros ac vulputate. In lorem velit, tempor nec vestibulum non, tempus ac lectus. Morbi dapibus neque eget est ullamcorper vulputate. Nam malesuada eros ut urna congue tincidunt. Suspendisse eu feugiat nulla. Sed convallis, libero a rhoncus viverra, lorem nunc efficitur justo, in vestibulum est nisi sed sapien. Nulla malesuada diam a justo ultricies viverra. Quisque nulla nunc, pretium et suscipit nec, ultricies a lacus." + System.lineSeparator() +
                        "Nulla ut risus nec nisl pharetra faucibus. Ut pretium egestas lacus. Sed vitae justo varius, pharetra lorem id, dignissim leo. Fusce interdum euismod elit, ac tempus purus ornare nec. Donec sed semper ligula. Sed enim justo, tempus ut condimentum vehicula, efficitur quis mauris. Suspendisse ultricies, tortor ac congue dignissim, elit tellus cursus enim, at faucibus turpis ligula eget mi. Fusce quis sapien et lectus ultrices imperdiet. In ac velit arcu. Curabitur vel euismod sapien.")
                .lecturer(lecturerUserBaseRepository.findByEmail(LecturerLoader.PROFESORDOKTOR_EMAIL).orElseThrow(RuntimeException::new))
                .build());
        newsRepository.save(News.builder()
                .title("Kolko IT")
                .description("Zapraszamy na kolko IT!" +
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras finibus mi ut augue mollis, id suscipit quam elementum. Donec vulputate sit amet eros ac vulputate. In lorem velit, tempor nec vestibulum non, tempus ac lectus. Morbi dapibus neque eget est ullamcorper vulputate. Nam malesuada eros ut urna congue tincidunt. Suspendisse eu feugiat nulla. Sed convallis, libero a rhoncus viverra, lorem nunc efficitur justo, in vestibulum est nisi sed sapien. Nulla malesuada diam a justo ultricies viverra. Quisque nulla nunc, pretium et suscipit nec, ultricies a lacus." + System.lineSeparator() +
                        "Nulla ut risus nec nisl pharetra faucibus. Ut pretium egestas lacus. Sed vitae justo varius, pharetra lorem id, dignissim leo. Fusce interdum euismod elit, ac tempus purus ornare nec. Donec sed semper ligula. Sed enim justo, tempus ut condimentum vehicula, efficitur quis mauris. Suspendisse ultricies, tortor ac congue dignissim, elit tellus cursus enim, at faucibus turpis ligula eget mi. Fusce quis sapien et lectus ultrices imperdiet. In ac velit arcu. Curabitur vel euismod sapien.")
                .lecturer(lecturerUserBaseRepository.findByEmail(LecturerLoader.PROFESORDOKTOR_EMAIL).orElseThrow(RuntimeException::new))
                .build());
    }
}
