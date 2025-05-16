package codereview.school_mate.mapper;

import codereview.school_mate.dto.HomeworkRequestDto;
import codereview.school_mate.dto.HomeworkResponseDto;
import codereview.school_mate.model.Homework;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HomeworkMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "schoolClass", ignore = true)
    Homework toEntity(HomeworkRequestDto dto);

    @Mapping(target = "subjectId", source = "subject.id")
    @Mapping(target = "classId", source = "schoolClass.id")
    HomeworkResponseDto toDto(Homework entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "schoolClass", ignore = true)
    void updateEntityFromDto(HomeworkRequestDto dto, @MappingTarget Homework entity);

    List<HomeworkResponseDto> toDtos(List<Homework> homeworks);

}
