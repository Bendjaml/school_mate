package codereview.school_mate.serviceImpl;

import codereview.school_mate.dto.HomeworkRequestDto;
import codereview.school_mate.dto.HomeworkResponseDto;
import codereview.school_mate.exception.NotFoundException;
import codereview.school_mate.model.Homework;
import codereview.school_mate.model.SchoolClass;
import codereview.school_mate.model.Subject;
import codereview.school_mate.repository.*;
import codereview.school_mate.service.serviceImpl.HomeworkServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import codereview.school_mate.repository.SchoolClassRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Testcontainers
@SpringBootTest
@Transactional
class HomeworkServiceImplTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private HomeworkServiceImpl homeworkService;
    @Autowired
    private HomeworkRepository homeworkRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private SchoolClassRepository schoolClassRepository;

    private Subject testSubject;
    private SchoolClass testClass;

    @BeforeEach
    void setUp() {
        testSubject = new Subject();
        testSubject.setName("Math");
        testSubject = subjectRepository.save(testSubject);

        testClass = new SchoolClass();
        testClass.setName("10-A");
        testClass = schoolClassRepository.save(testClass);
    }

    @Test
    void createHomework_ShouldSaveWithRelations() {
        HomeworkRequestDto request = new HomeworkRequestDto(
                LocalDateTime.now(), "Algebra HW", testSubject.getId(), testClass.getId()
        );

        HomeworkResponseDto result = homeworkService.createHomework(request);

        assertNotNull(result.getId());
        assertEquals("Algebra HW", result.getDescriptionHomeworks());
        assertEquals(testSubject.getId(), result.getSubject().getId());
    }

    @Test
    void createHomework_ShouldThrow_WhenSubjectNotFound() {
        HomeworkRequestDto request = new HomeworkRequestDto(
                LocalDateTime.now(), "Physics HW", 999L, testClass.getId()
        );

        assertThrows(NotFoundException.class, () -> homeworkService.createHomework(request));
    }

    @Test
    void findHomeworkById_ShouldReturn_WhenExists() {
        Homework saved = createTestHomework();
        HomeworkResponseDto result = homeworkService.findHomeworkById(saved.getId());

        assertEquals(saved.getId(), result.getId());
    }

    @Test
    void updateHomework_ShouldUpdateFields() {
        Homework saved = createTestHomework();
        HomeworkRequestDto update = new HomeworkRequestDto(
                LocalDateTime.now().plusDays(1), "Updated HW", testSubject.getId(), testClass.getId()
        );

        HomeworkResponseDto result = homeworkService.updateHomework(saved.getId(), update);

        assertEquals("Updated HW", result.getDescriptionHomeworks());
    }

    @Test
    void deleteHomework_ShouldRemoveFromDb() {
        Homework saved = createTestHomework();
        homeworkService.deleteHomework(saved.getId());

        assertFalse(homeworkRepository.existsById(saved.getId()));
    }

    private Homework createTestHomework() {
        Homework homework = new Homework();
        homework.setDescriptionHomeworks("Test HW");
        homework.setDate(LocalDateTime.now());
        homework.setSubject(testSubject);
        homework.setSchoolClass(testClass);
        return homeworkRepository.save(homework);
    }
}