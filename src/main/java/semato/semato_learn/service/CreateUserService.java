package semato.semato_learn.service;


import org.springframework.transaction.annotation.Transactional;
import semato.semato_learn.controller.payload.UserAddRequest;
import semato.semato_learn.exception.EmailAlreadyInUse;


public interface CreateUserService {

    @Transactional
    void addUser(UserAddRequest user) throws EmailAlreadyInUse;

}
