package semato.semato_learn.model.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import semato.semato_learn.model.Lecturer;
import semato.semato_learn.model.Publication;
import semato.semato_learn.model.repository.PublicationRepository;
import semato.semato_learn.model.repository.UserBaseRepository;

@Component
@Profile("!test")
@Order(6)
public class PublicationLoader implements ApplicationRunner {

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private UserBaseRepository<Lecturer> lecturerUserBaseRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        publicationRepository.save(Publication.builder()
                .title("Testy z matematyki - wady i zalety")
                .description("Coraz częstszym sposobem sprawdzenia wiedzy i umiejętności ucznia staje się test. Dotychczasowa " +
                        "forma zadań otwartych przechodzi jakby w zapomnienie. Najlepiej widać to u obecnych " +
                        "licealistów, którzy uczęszczali do gimnazjum. Ich umiejętność radzenia sobie z zadaniami, " +
                        "sposób prezentacji zadania, umiejętność uczenia się matematyki znacznie odbiega od uczniów, " +
                        "którzy kończyli ośmioklasową SP.")
                .lecturer(lecturerUserBaseRepository.findByEmail(LecturerLoader.EMAIL).orElseThrow(RuntimeException::new)).build());

        publicationRepository.save(Publication.builder()
                .title("Zestaw zadań z logiki na godzinny sprawdzian w różnych klasach")
                .description("Chciałabym się podzielić swoimi sprawdzianami z logiki. Ponieważ " +
                        "dopiero w nowym liceum mamy obowiązek uczenia logiki, liczba zadań z tego działu jest ograniczona. " +
                        "Sama miałam trudności z ułożeniem sprawdzianów i kartkówek, tym bardziej, że w tym roku szkolnym " +
                        "miałam 5 klas pierwszych. Wszyscy, którzy to przeszli dobrze mnie rozumieją. Znalezienie dobrych " +
                        "zadań nie jest łatwe, a jeszcze na tyle grup. Dlatego może moje propozycje komuś pomogą. Są naprawdę" +
                        " dobre")
                .lecturer(lecturerUserBaseRepository.findByEmail(LecturerLoader.EMAIL).orElseThrow(RuntimeException::new)).build());

        publicationRepository.save(Publication.builder()
                .title("Uczestnictwo nauczyciela i ucznia w interpretacji dzieła muzycznego")
                .description("Szkoły muzyczne I i II stopnia w Polsce są powołane do " +
                        "przygotowania młodzieży do zawodu artystycznego. Program nauczania obejmuje w zakresie danej" +
                        "specjalizacji instrumentalnej m.in. gruntowne, systematyczne zapoznawanie ucznia z " +
                        "obszernym materiałem nutowym, literaturą muzyczną, a nade wszystko zdobycie przezeń szeroko" +
                        "rozumianej umiejętności gry na wybranym instrumencie. Wiąże się to także z równoczesnym" +
                        "doskonaleniem sprawności manualnej i zdolnością jej sukcesywnego wykorzystywania" +
                        "w czasie gry, następnie z zagłębianiem się intelektualnym oraz emocjonalnym w treść" +
                        "utworów muzycznych, a także w arkana samego wykonawstwa, ocenianego jako dobre" +
                        "lub złe, o znamionach artystycznych lub pozbawione ich, czy też jako zaledwie poprawne...")
                .lecturer(lecturerUserBaseRepository.findByEmail(LecturerLoader.EMAIL).orElseThrow(RuntimeException::new)).build());
    }
}
