package codereview.school_mate.service.serviceImpl;

import codereview.school_mate.exception.NotFoundException;
import codereview.school_mate.model.Role;
import codereview.school_mate.repository.RoleRepository;
import codereview.school_mate.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() ->
                new NotFoundException("Role with name = " + name + " not found"));
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Role with id = " + id + " not found"));
    }
}
