package semato.semato_learn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import semato.semato_learn.controller.payload.PublicationRequest;
import semato.semato_learn.controller.payload.PublicationResponse;
import semato.semato_learn.model.Lecturer;
import semato.semato_learn.model.Publication;
import semato.semato_learn.model.Student;
import semato.semato_learn.model.User;
import semato.semato_learn.model.repository.PublicationRepository;
import semato.semato_learn.model.repository.UserBaseRepository;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private UserBaseRepository<Lecturer> lecturerBaseRepository;

    public List<PublicationResponse> getAllByLecturer(User user) {
        List<Publication> publications = publicationRepository.findAllByLecturerId(user.getId()).orElse(Collections.emptyList());
        return createPublicationResponse(publications);
    }

    public PublicationResponse getByIdAndLecturer(Long publicationId, User user) throws IllegalArgumentException {
        Publication publication = getPublication(publicationId, user);
        return PublicationResponse.create(publication);
    }

    public List<PublicationResponse> getAllByStudentGroup(User user) {
        if (user instanceof Student) {
            Student student = (Student) user;
            List<Publication> publications = publicationRepository.findAllByStudentGroup(student.getGroup().getId()).orElse(Collections.emptyList());
            return createPublicationResponse(publications);
        } else {
            throw new ClassCastException("User isn't instanceof Student!");
        }
    }

    public Publication add(PublicationRequest publicationRequest) {
        return publicationRepository.save(Publication.builder()
                .lecturer(lecturerBaseRepository.findById(publicationRequest.getLecturerId()).orElseThrow(() -> new IllegalArgumentException("Lecturer not found!")))
                .title(publicationRequest.getTitle())
                .description(publicationRequest.getDescription())
                .build());
    }

    public Publication edit(PublicationRequest publicationRequest, Long publicationId, User user) throws IllegalArgumentException {
        Publication publication = getPublication(publicationId, user);
        publication.setTitle(publicationRequest.getTitle());
        publication.setDescription(publicationRequest.getDescription());
        publication.setUpdatedAt(Instant.now());
        publicationRepository.save(publication);
        return publication;
    }

    public void delete(Long publicationId, User user) throws IllegalArgumentException {
        Publication publication = getPublication(publicationId, user);
        publication.setDeletedAt(Instant.now());
        publicationRepository.save(publication);
    }

    private Publication getPublication(Long publicationId, User user) throws IllegalArgumentException {
        Publication publication = publicationRepository.findById(publicationId).orElseThrow(() -> new IllegalArgumentException("Publication not found!"));
        if (!publication.getLecturer().getId().equals(user.getId())) {
            throw new IllegalArgumentException("This is not manage by this Lecturer " + user.getId() + "!");
        }
        return publication;
    }

    private List<PublicationResponse> createPublicationResponse(List<Publication> publications) {
        return publications.stream().map(PublicationResponse::create).collect(Collectors.toList());
    }
}
