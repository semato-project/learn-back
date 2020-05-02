package semato.semato_learn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import semato.semato_learn.controller.payload.PublicationCreateRequest;
import semato.semato_learn.controller.payload.PublicationEditRequest;
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

import static org.springframework.util.StringUtils.isEmpty;

@Service
public class PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private UserBaseRepository<Lecturer> lecturerBaseRepository;

    public List<PublicationResponse> getAllByLecturer(User user) {
        List<Publication> publications = publicationRepository.findAllByLecturerIdAndDeletedAtIsNull(user.getId()).orElse(Collections.emptyList());
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

    public PublicationResponse add(PublicationCreateRequest publicationCreateRequest, User user) {
        Publication publication = publicationRepository.save(Publication.builder()
                .lecturer(lecturerBaseRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("Lecturer not found!")))
                .title(publicationCreateRequest.getTitle())
                .description(publicationCreateRequest.getDescription())
                .build());
        return PublicationResponse.create(publication);
    }

    public PublicationResponse edit(PublicationEditRequest publicationEditRequest, Long publicationId, User user) throws IllegalArgumentException {
        Publication publication = getPublication(publicationId, user);
        if(!isEmpty(publicationEditRequest.getTitle())) { publication.setTitle(publicationEditRequest.getTitle()); }
        if(!isEmpty(publicationEditRequest.getDescription())) { publication.setDescription(publicationEditRequest.getDescription()); }
        publication.setUpdatedAt(Instant.now());
        publicationRepository.save(publication);
        return PublicationResponse.create(publication);
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
