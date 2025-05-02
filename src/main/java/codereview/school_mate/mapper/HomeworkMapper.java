package codereview.school_mate.mapper;

import codereview.school_mate.dto.HomeworkRequestDto;
import codereview.school_mate.dto.HomeworkResponseDto;
import codereview.school_mate.model.Homework;
import codereview.school_mate.model.SchoolClass;
import codereview.school_mate.model.Subject;
import codereview.school_mate.repository.SchoolClassRepository;
import codereview.school_mate.repository.SubjectRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class HomeworkMapper {

    @Autowired
    protected SchoolClassRepository schoolClassRepository;

    @Autowired
    protected SubjectRepository subjectRepository;

    @Mapping(target = "schoolClass", expression = "java(getSchoolClass(dto.getClassId()))")
    @Mapping(target = "subject", expression = "java(getSubject(dto.getSubjectId()))")
    public abstract Homework toEntity(HomeworkRequestDto dto);

    @Mapping(target = "schoolClass", source = "schoolClass")
    @Mapping(target = "subject", source = "subject")
    public abstract HomeworkResponseDto toDto(Homework entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "schoolClass", expression = "java(getSchoolClass(dto.getClassId()))")
    @Mapping(target = "subject", expression = "java(getSubject(dto.getSubjectId()))")
    public abstract void updateEntityFromDto(HomeworkRequestDto dto, @MappingTarget Homework entity);

    public abstract List<HomeworkResponseDto> toDtos(List<Homework> homeworks);

    protected SchoolClass getSchoolClass(Long id) {
        return id != null ? schoolClassRepository.findById(id).orElse(null) : null;
    }

    protected Subject getSubject(Long id) {
        return id != null ? subjectRepository.findById(id).orElse(null) : null;
    }
}
