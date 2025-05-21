package codereview.school_mate.mapper;

import codereview.school_mate.dto.request.SchoolClassRequestDto;
import codereview.school_mate.dto.responce.SchoolClassResponseDto;
import codereview.school_mate.model.SchoolClass;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SchoolClassMapper {
    SchoolClass toEntity(SchoolClassRequestDto dto);
    SchoolClassResponseDto toDto(SchoolClass entity);
    void updateEntityFromDto(SchoolClassRequestDto dto, @MappingTarget SchoolClass entity);
    List<SchoolClassResponseDto> toDtos(List<SchoolClass> entities);
}
