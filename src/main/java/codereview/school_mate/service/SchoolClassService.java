package codereview.school_mate.service;

import codereview.school_mate.dto.SchoolClassRequestDto;
import codereview.school_mate.dto.SchoolClassResponseDto;
import codereview.school_mate.model.SchoolClass;

import java.util.List;

public interface SchoolClassService {
    SchoolClassResponseDto createSchoolClass(SchoolClassRequestDto dto);
    SchoolClassResponseDto findByDtoSchoolClassId(Long id);
    SchoolClass findSchoolClassBySubjectId(Long id);
    List<SchoolClassResponseDto> findAllSchoolClasses();
    SchoolClassResponseDto updateSchoolClass(Long id, SchoolClassRequestDto dto);
    void deleteSchoolClass(Long id);
}
