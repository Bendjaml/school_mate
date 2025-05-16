package codereview.school_mate.serviceImpl;

import codereview.school_mate.dto.SubjectRequestDto;
import codereview.school_mate.dto.SubjectResponseDto;
import codereview.school_mate.model.Subject;
import codereview.school_mate.repository.SubjectRepository;
import codereview.school_mate.service.serviceImpl.SubjectServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


@Testcontainers
@SpringBootTest
@Transactional
class SubjectServiceImplTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private SubjectServiceImpl subjectService;

    @Autowired
    private SubjectRepository subjectRepository;

    private Subject testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Subject();
        testSubject.setName("Mathematics");
        testSubject = subjectRepository.save(testSubject);
    }

    @Test
    @DisplayName("Создание предмета - успешный сценарий")
    void createSubject_ShouldSaveSubject() {
        SubjectRequestDto request = new SubjectRequestDto();
        request.setName("Physics");

        SubjectResponseDto result = subjectService.createSubject(request);

        assertNotNull(result.getId());
        assertEquals("Physics", result.getName());
        assertTrue(subjectRepository.existsById(result.getId()));
    }

    @Test
    @DisplayName("Получение предмета по ID - успешный сценарий")
    void findDtoBySubject_ShouldReturnSubject_Id_WhenExists() {
        SubjectResponseDto result = subjectService.findDtoBySubjectId(testSubject.getId());

        assertEquals(testSubject.getId(), result.getId());
        assertEquals("Mathematics", result.getName());
    }

    @Test
    @DisplayName("Получение предмета по ID - предмет не найден")
    void findDtoBySubject_Id_ShouldThrow_WhenNotExists() {
        assertThrows(RuntimeException.class, () -> subjectService.findDtoBySubjectId(999L));
    }

    @Test
    @DisplayName("Получение всех предметов")
    void findAllSubject_ShouldReturnAllSubjects() {
        Subject anotherSubject = new Subject();
        anotherSubject.setName("Chemistry");
        subjectRepository.save(anotherSubject);

        List<SubjectResponseDto> result = subjectService.findAllSubject();

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(s -> s.getName().equals("Mathematics")));
        assertTrue(result.stream().anyMatch(s -> s.getName().equals("Chemistry")));
    }

    @Test
    @DisplayName("Обновление предмета - успешный сценарий")
    void updateSubject_ShouldUpdateSubject() {
        SubjectRequestDto updateRequest = new SubjectRequestDto();
        updateRequest.setName("Advanced Math");

        SubjectResponseDto result = subjectService.updateSubject(testSubject.getId(), updateRequest);

        assertEquals(testSubject.getId(), result.getId());
        assertEquals("Advanced Math", result.getName());
    }

    @Test
    @DisplayName("Обновление предмета - предмет не найден")
    void updateSubject_ShouldThrow_WhenNotExists() {
        SubjectRequestDto updateRequest = new SubjectRequestDto();
        updateRequest.setName("Biology");

        assertThrows(RuntimeException.class,
                () -> subjectService.updateSubject(999L, updateRequest));
    }

    @Test
    @DisplayName("Удаление предмета - успешный сценарий")
    void deleteSubject_ShouldRemoveSubject() {
        subjectService.deleteSubject(testSubject.getId());

        assertFalse(subjectRepository.existsById(testSubject.getId()));
    }

    @Test
    @DisplayName("Удаление предмета - предмет не найден")
    void deleteSubject_ShouldNotThrow_WhenNotExists() {
        assertDoesNotThrow(() -> subjectService.deleteSubject(999L));
    }

    @Test
    @DisplayName("Создание предмета - проверка валидации")
    void createSubject_ShouldThrow_WhenInvalidData() {
        SubjectRequestDto invalidRequest = new SubjectRequestDto();
        invalidRequest.setName(""); // Пустое имя

        assertThrows(Exception.class, () -> subjectService.createSubject(invalidRequest));
    }
}