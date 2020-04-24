package semato.semato_learn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import semato.semato_learn.controller.payload.GroupResponse;
import semato.semato_learn.model.Group;
import semato.semato_learn.model.repository.GroupRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public List<GroupResponse> getAllGroups(){
        List<Group> groups = groupRepository.findAll();
        return createGroupsResponse(groups);
    }

    public GroupResponse getGroup(Long id) throws IllegalArgumentException {
        Group group = groupRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Group not found!"));
        return GroupResponse.create(group);
    }

    private List<GroupResponse> createGroupsResponse(List<Group> groups) {
        return groups.stream().map(GroupResponse::create).collect(Collectors.toList());
    }

}
