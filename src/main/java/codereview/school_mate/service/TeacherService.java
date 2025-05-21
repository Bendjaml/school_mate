package codereview.school_mate.service;

import codereview.school_mate.dto.request.registration.TeacherRegistrationRequestDto;
import codereview.school_mate.dto.request.TeacherRequestDto;
import codereview.school_mate.dto.responce.TeacherResponseDto;
import codereview.school_mate.model.User;

import java.util.List;

public interface TeacherService {
    TeacherResponseDto createTeacher(TeacherRegistrationRequestDto dto, User user);
    TeacherResponseDto findByIdTeacher(Long id);
    List<TeacherResponseDto> findAllTeacher();
    TeacherResponseDto updateTeacher(Long id, TeacherRequestDto dto);
    void deleteTeacher(Long id);
    TeacherResponseDto addSubjectToTeacher(Long teacherId, Long subjectId);
}
