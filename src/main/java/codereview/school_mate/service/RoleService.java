package codereview.school_mate.service;

import codereview.school_mate.model.Role;

public interface RoleService {
    Role findByName(String name);
    Role findById(Long id);
}
