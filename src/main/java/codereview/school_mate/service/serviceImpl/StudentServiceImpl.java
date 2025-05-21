package codereview.school_mate.service.serviceImpl;

import codereview.school_mate.dto.request.registration.StudentRegistrationRequestDto;
import codereview.school_mate.dto.request.StudentRequestDto;
import codereview.school_mate.dto.responce.StudentResponseDto;
import codereview.school_mate.exception.NotFoundException;
import codereview.school_mate.mapper.StudentMapper;
import codereview.school_mate.model.Parent;
import codereview.school_mate.model.SchoolClass;
import codereview.school_mate.model.Student;
import codereview.school_mate.model.User;
import codereview.school_mate.repository.ParentRepository;
import codereview.school_mate.repository.SchoolClassRepository;
import codereview.school_mate.repository.StudentRepository;
import codereview.school_mate.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final ParentRepository parentRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final StudentMapper studentMapper;

    @Override
    @Transactional
    public StudentResponseDto createStudent(StudentRegistrationRequestDto dto, User user) {
        SchoolClass schoolClass = schoolClassRepository.findById(dto.getSchoolClassId())
                .orElseThrow(() -> new NotFoundException("SchoolClass not found with id: " + dto.getSchoolClassId()));

        Student student = studentMapper.registrationDtoToStudent(dto);
        student.setUser(user);
        student.setSchoolClass(schoolClass);

        return studentMapper.studentToStudentResponseDto(studentRepository.save(student));
    }

    @Override
    public StudentResponseDto findByIdStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found with id: " + id));
        return studentMapper.studentToStudentResponseDto(student);
    }

    @Override
    public List<StudentResponseDto> findAllStudent() {
        return studentMapper.studentsToStudentResponseDtos(studentRepository.findAll());
    }

    @Override
    @Transactional
    public StudentResponseDto updateStudent(Long id, StudentRequestDto dto) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found"));

        if (dto.getParentId() != null) {
            Parent parent = parentRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new NotFoundException("Parent not found"));
            existingStudent.setParent(parent);
        }

        if (dto.getSchoolClassId() != null) {
            SchoolClass schoolClass = schoolClassRepository.findById(dto.getSchoolClassId())
                    .orElseThrow(() -> new NotFoundException("SchoolClass not found"));
            existingStudent.setSchoolClass(schoolClass);
        }

        studentMapper.updateEntityFromDto(dto, existingStudent);
        Student updatedStudent = studentRepository.save(existingStudent);
        return studentMapper.studentToStudentResponseDto(updatedStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}