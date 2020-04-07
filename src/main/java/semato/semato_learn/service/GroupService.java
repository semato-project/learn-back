package semato.semato_learn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import semato.semato_learn.model.Group;
import semato.semato_learn.model.repository.GroupRepository;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public List<Group> getAllGroups(){
        return groupRepository.findAll();
    }

    public Group getGroup(Long id) throws IllegalArgumentException {
        return groupRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Group not found!"));
    }

}
