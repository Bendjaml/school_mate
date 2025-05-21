package codereview.school_mate.service;

import codereview.school_mate.dto.request.registration.RegistrationRequestDto;
import codereview.school_mate.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    User create(RegistrationRequestDto registrationRequestDto);
    Optional<User> findByUsername(String username);
}
