package semato.semato_learn.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import semato.semato_learn.SematoLearnApplication;
import semato.semato_learn.controller.payload.PublicationRequest;
import semato.semato_learn.controller.payload.PublicationResponse;
import semato.semato_learn.model.*;
import semato.semato_learn.model.repository.GroupRepository;
import semato.semato_learn.model.repository.PublicationRepository;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SematoLearnApplication.class)
@Transactional
public class PublicationServiceTest {

    @Autowired
    private PublicationService publicationService;

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private MockService mockService;

    @Autowired
    private GroupRepository groupRepository;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();


    @Test
    public void shouldReturnListOfAllPublication_andForByLecturer() {
        Lecturer lecturer1 = mockService.mockLecturer();
        Lecturer lecturer2 = mockService.mockLecturer("lecturer2@gmail.com");
        publicationRepository.save(Publication.builder()
                .title("Publikacja1!")
                .description("Opis publikacji")
                .lecturer(lecturer1)
                .build());
        publicationRepository.save(Publication.builder()
                .title("Publikacja2!")
                .description("Opis publikacji")
                .lecturer(lecturer1)
                .build());
        publicationRepository.save(Publication.builder()
                .title("Publikacja3!")
                .description("Opis publikacji")
                .lecturer(lecturer2)
                .build());

        List<PublicationResponse> allPublicationByLecturer1 = publicationService.getAllByLecturer(lecturer1);
        assertEquals(2, allPublicationByLecturer1.size());
    }

    @Test
    public void shouldReturnListOfPublication_andByStudent() {
        Group group1 = new Group();
        Lecturer lecturer1 = mockService.mockLecturer();
        Course course1 = mockService.mockCourse(lecturer1);
        course1.setGroup(group1);
        groupRepository.save(group1);
        Student student1 = mockService.mockStudent(group1);
        publicationRepository.save(Publication.builder()
                .title("Publikacja1!")
                .description("Opis publikacji")
                .lecturer(lecturer1)
                .build());
        publicationRepository.save(Publication.builder()
                .title("Publikacja2!")
                .description("Opis publikacji")
                .lecturer(lecturer1)
                .build());

        Group group2 = new Group();
        Lecturer lecturer2 = mockService.mockLecturer("second@gmail.com");
        Course course2 = mockService.mockCourse(lecturer2);
        course2.setGroup(group2);
        groupRepository.save(group2);
        Student student2 = mockService.mockStudent(group2, "secondStudent@gmail.com");
        publicationRepository.save(Publication.builder()
                .title("Publikacja3!")
                .description("Opis publikacji")
                .lecturer(lecturer2)
                .build());

        List<PublicationResponse> student1Publication = publicationService.getAllByStudentGroup(student1);
        List<PublicationResponse> student2Publication = publicationService.getAllByStudentGroup(student2);
        assertEquals(2, student1Publication.size());
        assertEquals(1, student2Publication.size());
    }

    @Test
    public void whenGetByIdAndLecturer_givenNonExistsPublicationId_thenThrowsIllegalArgumentException() {
        //given
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Publication not found!");

        Lecturer lecturer = mockService.mockLecturer();
        Long notExistsPublicationId = 100L;

        //when
        publicationService.getByIdAndLecturer(notExistsPublicationId, lecturer);
    }

    @Test
    public void whenGetByIdAndLecturer_givenExistsPublicationIdManagedByOtherLecturer_thenThrowsIllegalArgumentException() {
        //given
        Lecturer lecturer1 = mockService.mockLecturer();
        Lecturer lecturer2 = mockService.mockLecturer("other@gmail.com");

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("This is not manage by this Lecturer " + lecturer2.getId() + "!");
        Publication publication = publicationRepository.save(Publication.builder()
                .title("Koronawirus!")
                .description("Z powodu koronawirusa zostancie w domu!")
                .lecturer(lecturer1)
                .build());

        //when
        publicationService.getByIdAndLecturer(publication.getId(), lecturer2);
    }


    @Test
    public void whenAdd_givenPublicationRequest_thenSavePublication() {
        //given
        Lecturer lecturer1 = mockService.mockLecturer();
        PublicationRequest publicationRequest = new PublicationRequest(lecturer1.getId(), "Title", "Description");

        //when
        Publication publication = publicationService.add(publicationRequest);

        //then
        assertTrue(publicationRepository.findById(publication.getId()).isPresent());
    }

    @Test
    public void whenDelete_givenPublicationId_thenDeletePublication() {
        //given
        Lecturer lecturer1 = mockService.mockLecturer();
        Publication publication= publicationRepository.save(Publication.builder()
                .title("Publikacja1!")
                .description("Opis publikacji")
                .lecturer(lecturer1)
                .build());

        //when
        publicationService.delete(publication.getId(), lecturer1);

        //then
        assertTrue(publicationRepository.findById(publication.getId()).isPresent());
        assertNotNull(publicationRepository.findById(publication.getId()).get().getDeletedAt());
    }

    @Test
    public void whenEdit_givenPublicationIdAndPublicationRequest_thenUpdatePublication() {
        //given
        Lecturer lecturer1 = mockService.mockLecturer();
        PublicationRequest publicationRequest = new PublicationRequest(lecturer1.getId(), "Title", "Description");
        Publication publication= publicationRepository.save(Publication.builder()
                .title("Publikacja1!")
                .description("Opis publikacji")
                .lecturer(lecturer1)
                .build());

        //when
        publicationService.edit(publicationRequest, publication.getId(), lecturer1);

        //then
        assertEquals("Title", publicationRepository.findById(publication.getId()).get().getTitle());
    }

}