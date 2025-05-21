package codereview.school_mate.service.serviceImpl;

import codereview.school_mate.dto.request.HomeworkRequestDto;
import codereview.school_mate.dto.responce.HomeworkResponseDto;
import codereview.school_mate.exception.NotFoundException;
import codereview.school_mate.mapper.HomeworkMapper;
import codereview.school_mate.model.Homework;
import codereview.school_mate.model.SchoolClass;
import codereview.school_mate.model.Subject;
import codereview.school_mate.repository.HomeworkRepository;
import codereview.school_mate.service.HomeworkService;
import codereview.school_mate.service.SchoolClassService;
import codereview.school_mate.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeworkServiceImpl implements HomeworkService {
    private final HomeworkRepository homeworkRepository;
    private final HomeworkMapper homeworkMapper;
    private final SchoolClassService schoolClassService;
    private final SubjectService subjectService;

    @Override
    @Transactional
    public HomeworkResponseDto createHomework(HomeworkRequestDto dto) {
        Homework homework = homeworkMapper.toEntity(dto);

        Subject subject = subjectService.findSubjectBySubjectId(dto.getSubjectId());
        SchoolClass schoolClass = schoolClassService.findSchoolClassBySubjectId(dto.getClassId());

        homework.setSubject(subject);
        homework.setSchoolClass(schoolClass);

        Homework savedHomework = homeworkRepository.save(homework);
        return homeworkMapper.toDto(savedHomework);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HomeworkResponseDto> findAllHomework() {
        return homeworkMapper.toDtos(homeworkRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HomeworkResponseDto> findHomeworkByClassId(Long classId) {
        schoolClassService.findSchoolClassBySubjectId(classId);
        return homeworkMapper.toDtos(homeworkRepository.findBySchoolClassId(classId));
    }

    @Override
    @Transactional(readOnly = true)
    public HomeworkResponseDto findHomeworkById(Long id) {
        Homework homework = homeworkRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Homework not found with ID: " + id));
        return homeworkMapper.toDto(homework);
    }

    @Override
    @Transactional
    public HomeworkResponseDto updateHomework(Long id, HomeworkRequestDto dto) {
        Homework homework = homeworkRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Homework not found with ID: " + id));

        if (!dto.getSubjectId().equals(homework.getSubject().getId())) {
            Subject subject = subjectService.findSubjectBySubjectId(dto.getSubjectId());
            homework.setSubject(subject);
        }

        if (!dto.getClassId().equals(homework.getSchoolClass().getId())) {
            SchoolClass schoolClass = schoolClassService.findSchoolClassBySubjectId(dto.getClassId());
            homework.setSchoolClass(schoolClass);
        }

        homeworkMapper.updateEntityFromDto(dto, homework);
        return homeworkMapper.toDto(homeworkRepository.save(homework));
    }

    @Override
    @Transactional
    public void deleteHomework(Long id) {
        if (!homeworkRepository.existsById(id)) {
            throw new NotFoundException("Homework not found with ID: " + id);
        }
        homeworkRepository.deleteById(id);
    }
}