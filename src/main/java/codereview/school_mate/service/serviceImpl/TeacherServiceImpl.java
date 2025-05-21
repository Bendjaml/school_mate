package codereview.school_mate.service.serviceImpl;

import codereview.school_mate.dto.request.registration.TeacherRegistrationRequestDto;
import codereview.school_mate.dto.request.TeacherRequestDto;
import codereview.school_mate.dto.responce.TeacherResponseDto;
import codereview.school_mate.exception.NotFoundException;
import codereview.school_mate.mapper.TeacherMapper;
import codereview.school_mate.model.Subject;
import codereview.school_mate.model.Teacher;
import codereview.school_mate.model.User;
import codereview.school_mate.repository.SubjectRepository;
import codereview.school_mate.repository.TeacherRepository;
import codereview.school_mate.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final SubjectRepository subjectRepository;


    @Override
    @Transactional
    public TeacherResponseDto createTeacher(TeacherRegistrationRequestDto dto, User user) {
        Teacher teacher = teacherMapper.registrationDtoToTeacher(dto);
        teacher.setUser(user);
        return teacherMapper.toDto(teacherRepository.save(teacher));
    }

    @Override
    public TeacherResponseDto findByIdTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found"));//коментарий полнее с данными по id
        return teacherMapper.toDto(teacher);
    }

    @Override
    public List<TeacherResponseDto> findAllTeacher() {
        return teacherMapper.teachersToTeacherResponseDtos(teacherRepository.findAll());
    }

    @Override
    @Transactional
    public TeacherResponseDto updateTeacher(Long id, TeacherRequestDto dto) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found"));
        teacherMapper.updateEntityFromDto(dto, teacher);
        return teacherMapper.toDto(teacherRepository.save(teacher));
    }

    @Override
    @Transactional
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    @Override
    @Transactional
    public TeacherResponseDto addSubjectToTeacher(Long teacherId, Long subjectId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException("Teacher not found"));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new NotFoundException("Subject not found"));

        teacher.getSubjects().add(subject);
        Teacher savedTeacher = teacherRepository.save(teacher);
        return teacherMapper.toDto(savedTeacher);
    }
}
