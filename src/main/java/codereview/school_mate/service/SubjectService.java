package codereview.school_mate.service;

import codereview.school_mate.dto.request.SubjectRequestDto;
import codereview.school_mate.dto.responce.SubjectResponseDto;
import codereview.school_mate.model.Subject;
import java.util.List;

public interface SubjectService {
    SubjectResponseDto createSubject(SubjectRequestDto dto);
    SubjectResponseDto findDtoBySubjectId(Long id);
    Subject findSubjectBySubjectId(Long id);
    List<SubjectResponseDto> findAllSubject();
    SubjectResponseDto updateSubject(Long id, SubjectRequestDto dto);
    void deleteSubject(Long id);
}