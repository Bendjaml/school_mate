package codereview.school_mate.service;

import codereview.school_mate.dto.request.registration.StudentRegistrationRequestDto;
import codereview.school_mate.dto.request.StudentRequestDto;
import codereview.school_mate.dto.responce.StudentResponseDto;
import codereview.school_mate.model.User;

import java.util.List;

public interface StudentService {
        StudentResponseDto createStudent(StudentRegistrationRequestDto dto, User user);
        StudentResponseDto findByIdStudent(Long id);
        List<StudentResponseDto> findAllStudent();
        StudentResponseDto updateStudent(Long id, StudentRequestDto dto);
        void deleteStudent(Long id);
    }