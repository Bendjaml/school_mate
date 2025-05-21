package codereview.school_mate.serviceImpl;

import codereview.school_mate.dto.request.registration.ParentRegistrationRequestDto;
import codereview.school_mate.dto.request.registration.RegistrationRequestDto;
import codereview.school_mate.dto.responce.ParentResponseDto;
import codereview.school_mate.mapper.ParentMapper;
import codereview.school_mate.model.Parent;
import codereview.school_mate.model.Role;
import codereview.school_mate.model.User;
import codereview.school_mate.repository.ParentRepository;
import codereview.school_mate.repository.RoleRepository;
import codereview.school_mate.service.UserService;
import codereview.school_mate.service.serviceImpl.ParentServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@Testcontainers
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
class ParentServiceTest {

    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private ParentMapper parentMapper;
    @Autowired
    private ParentServiceImpl parentService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withReuse(true);

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.hikari.max-lifetime", () -> "15000");
        registry.add("spring.datasource.hikari.connection-timeout", () -> "5000");
    }

    @BeforeEach
    void setUp() {
        parentRepository.deleteAll();
    }

    @AfterAll
    static void tearDown() {
        postgres.stop();
    }

    @Test
    void create_ValidRequest_ReturnsParentResponseDto() {
        ParentRegistrationRequestDto request = createValidParentRequest();
        RegistrationRequestDto registrationRequestDto = new RegistrationRequestDto();
        registrationRequestDto.setUsername(request.getUsername());
        registrationRequestDto.setPassword(request.getPassword());
        registrationRequestDto.setRole(request.getRole());

        Role role = new Role();
        role.setName(request.getRole());
        roleRepository.save(role);

        User user = userService.create(registrationRequestDto);

        ParentResponseDto result = parentService.createParent(request, user);

        assertNotNull(result.getId());
        assertEquals(request.getName(), result.getName());
        assertEquals(1, parentRepository.count());
    }

    @Test
    void create_MissingContacts_ThrowsException() {
        ParentRegistrationRequestDto request = createValidParentRequest();
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        request.setContacts(null);

        assertThrows(DataIntegrityViolationException.class, () -> {
            parentService.createParent(request, user);
        });
    }

    @Test
    @Transactional
    void findById_ExistingId_ReturnsParent() {
        Parent parent = new Parent();
        parent.setName("Test");
        parent.setSurname("Parent");
        parent.setPatronymic("Testovich");
        parent.setContacts("test@test.com");
        parent = parentRepository.save(parent);

        ParentResponseDto result = parentService.findByIdParent(parent.getId());

        assertEquals(parent.getId(), result.getId());
        assertEquals("Test", result.getName());
    }

    @Test
    void findById_NonExistingId_ThrowsException() {
        assertThrows(RuntimeException.class, () -> parentService.findByIdParent(999L));
    }

    private ParentRegistrationRequestDto createValidParentRequest() {
        ParentRegistrationRequestDto request = new ParentRegistrationRequestDto();
        request.setUsername("parent");
        request.setPassword("parentPass");
        request.setRole("PARENT");
        request.setName("John");
        request.setSurname("Doe");
        request.setPatronymic("Smith");
        request.setContacts("john@example.com");
        return request;
    }
}