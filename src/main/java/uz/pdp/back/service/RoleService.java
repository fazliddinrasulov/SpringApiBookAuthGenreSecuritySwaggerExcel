package uz.pdp.back.service;

import org.springframework.stereotype.Service;
import uz.pdp.back.entity.Role;

@Service
public interface RoleService {
    Role save(Role role);
}
