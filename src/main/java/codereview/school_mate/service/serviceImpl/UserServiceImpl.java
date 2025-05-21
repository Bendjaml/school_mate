package codereview.school_mate.service.serviceImpl;

import codereview.school_mate.dto.request.registration.RegistrationRequestDto;
import codereview.school_mate.exception.NotFoundException;
import codereview.school_mate.model.Role;
import codereview.school_mate.model.User;
import codereview.school_mate.repository.UserRepository;
import codereview.school_mate.service.RoleService;
import codereview.school_mate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public User create(RegistrationRequestDto registrationRequestDto) {
        User user = new User();
        user.setUsername(registrationRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationRequestDto.getPassword()));

        Role role = roleService.findByName(registrationRequestDto.getRole());

        user.setRole(role);

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.orElseThrow(() -> new NotFoundException("User with username = " + username + " not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()))
        );
    }
}
