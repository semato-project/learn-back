package semato.semato_learn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import semato.semato_learn.controller.payload.UserAddRequest;
import semato.semato_learn.exception.EmailAlreadyInUse;
import semato.semato_learn.model.Lecturer;
import semato.semato_learn.model.RoleName;
import semato.semato_learn.model.User;
import semato.semato_learn.model.repository.UserBaseRepository;

@Service
public class CreateLecturerService implements CreateUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserBaseRepository<User> userRepository;

    @Override
    public void addUser(UserAddRequest user) throws EmailAlreadyInUse {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyInUse();
        }
        userRepository.save(
                Lecturer.builder()
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .password(passwordEncoder.encode(user.getPassword()))
                        .role(RoleName.ROLE_LECTURER)
                        .build()
        );
    }
}
