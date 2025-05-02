package codereview.school_mate.service.serviceImpl;

import codereview.school_mate.dto.HomeworkRequestDto;
import codereview.school_mate.dto.HomeworkResponseDto;
import codereview.school_mate.mapper.HomeworkMapper;
import codereview.school_mate.model.Homework;
import codereview.school_mate.repository.HomeworkRepository;
import codereview.school_mate.service.HomeworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeworkServiceImpl implements HomeworkService {
    private final HomeworkRepository homeworkRepository;
    private final HomeworkMapper homeworkMapper;

    @Override
    @Transactional
    public HomeworkResponseDto createHomework(HomeworkRequestDto dto) {
        Homework homework = homeworkMapper.toEntity(dto);
        Homework homeworkSaved = homeworkRepository.save(homework);
        return homeworkMapper.toDto(homeworkSaved);
    }

    @Override
    @Transactional
    public List<HomeworkResponseDto> findAllHomework() {
        return homeworkRepository.findAll().stream()
                .map(homeworkMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<HomeworkResponseDto> findHomeworkByClassId(Long classId) {
        List<Homework> homeworks = homeworkRepository.findBySchoolClassId(classId);

        if (homeworks.isEmpty()) {
            throw new RuntimeException("No homeworks found for class ID: " + classId);
        }

        return homeworks.stream()
                .map(homeworkMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public HomeworkResponseDto findHomeworkById(Long id) {
        Homework homework = homeworkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Homework not found with ID: " + id));
        return homeworkMapper.toDto(homework);
    }

    @Override
    @Transactional
    public HomeworkResponseDto updateHomework(Long id, HomeworkRequestDto dto) {
        Homework homework = homeworkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Homework not found with ID: " + id));

        homeworkMapper.updateEntityFromDto(dto, homework);
        Homework updatedHomework = homeworkRepository.save(homework);
        return homeworkMapper.toDto(updatedHomework);
    }

    @Override
    @Transactional
    public void deleteHomework(Long id) {homeworkRepository.deleteById(id);}
}
