package codereview.school_mate.service.serviceImpl;

import codereview.school_mate.dto.request.SchoolClassRequestDto;
import codereview.school_mate.dto.responce.SchoolClassResponseDto;
import codereview.school_mate.mapper.SchoolClassMapper;
import codereview.school_mate.model.SchoolClass;
import codereview.school_mate.repository.SchoolClassRepository;
import codereview.school_mate.service.SchoolClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolClassServiceImpl implements SchoolClassService {
    private final SchoolClassRepository schoolClassRepository;
    private final SchoolClassMapper schoolClassMapper;

    @Override
    public SchoolClassResponseDto createSchoolClass(SchoolClassRequestDto dto) {
        SchoolClass schoolClass = schoolClassMapper.toEntity(dto);
        SchoolClass savedSchoolClass = schoolClassRepository.save(schoolClass);
        return schoolClassMapper.toDto(savedSchoolClass);
    }

    @Override
    public SchoolClassResponseDto findByDtoSchoolClassId(Long id) {
        SchoolClass schoolClass = schoolClassRepository.findById(id)
                .orElseThrow(()-> new NullPointerException("SchoolClass not found" + id));
        return schoolClassMapper.toDto(schoolClass);
    }

    @Override
    public SchoolClass findSchoolClassBySubjectId(Long id) {
        return schoolClassRepository.findById(id)
                .orElseThrow(()-> new NullPointerException("SchoolClass not found" + id));
    }

    @Override
    public List<SchoolClassResponseDto> findAllSchoolClasses() {
        return schoolClassMapper.toDtos(schoolClassRepository.findAll());
    }

    @Override
    @Transactional
    public SchoolClassResponseDto updateSchoolClass(Long id, SchoolClassRequestDto dto) {
        SchoolClass schoolClass = schoolClassRepository.findById(id)
                .orElseThrow(()-> new NullPointerException("SchoolClass not found" + id));
        schoolClassMapper.updateEntityFromDto(dto, schoolClass);
        return schoolClassMapper.toDto(schoolClassRepository.save(schoolClass));
    }

    @Override
    @Transactional
    public void deleteSchoolClass(Long id) {
        schoolClassRepository.deleteById(id);
    }
}
