package codereview.school_mate.serviceImpl;

import codereview.school_mate.dto.HomeworkRequestDto;
import codereview.school_mate.dto.HomeworkResponseDto;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Testcontainers
@SpringBootTest
@Transactional
class HomeworkServiceImplIntegrationTest {

    @Autowired
    private HomeworkServiceImpl homeworkService;
    @Autowired
    private HomeworkRepository homeworkRepository;
    @Autowired
    private SchoolClassRepository schoolClassRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    private SchoolClass testClass;
    private Subject testSubject;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }


    @BeforeEach
    void setUp() {
        testClass = new SchoolClass();
        testClass.setName("Test Class");
        testClass = schoolClassRepository.save(testClass);

        testSubject = new Subject();
        testSubject.setName("Math");
        testSubject = subjectRepository.save(testSubject);
    }

    @Test
    void findAllHomework_ShouldReturnAllHomeworks() {
        createTestHomework();
        createTestHomework();

        List<HomeworkResponseDto> result = homeworkService.findAllHomework();

        assertEquals(2, result.size());
    }

    @Test
    void findHomeworkByClassId_ShouldFilterByClass() {
        HomeworkRequestDto hw1 = createTestHomework();
        SchoolClass anotherClass = schoolClassRepository.save(new SchoolClass());
        Homework hw2 = new Homework();
        hw2.setSchoolClass(anotherClass);
        homeworkRepository.save(hw2);

        List<HomeworkResponseDto> result = homeworkService.findHomeworkByClassId(testClass.getId());

        assertEquals(1, result.size());
        assertEquals(hw1.getClassId(), result.get(0).getId());
    }

    private HomeworkRequestDto createTestHomework() {
        HomeworkRequestDto homeworkRequestDto = new HomeworkRequestDto();
        homeworkRequestDto.setClassId(testClass.getId());
        homeworkRequestDto.setSubjectId(testSubject.getId());
        homeworkRequestDto.setDescription("test description");
        homeworkRequestDto.setDate(LocalDateTime.now());
        return homeworkRequestDto;
    }
}