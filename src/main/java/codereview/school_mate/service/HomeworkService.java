package codereview.school_mate.service;

import codereview.school_mate.dto.request.HomeworkRequestDto;
import codereview.school_mate.dto.responce.HomeworkResponseDto;

import java.util.List;

public interface HomeworkService {
    HomeworkResponseDto createHomework(HomeworkRequestDto dto);
    List<HomeworkResponseDto> findAllHomework();
    List<HomeworkResponseDto> findHomeworkByClassId(Long classId);
    HomeworkResponseDto findHomeworkById(Long id);
    HomeworkResponseDto updateHomework(Long id, HomeworkRequestDto dto);
    void deleteHomework(Long id);


}
