package codereview.school_mate.dto;

import codereview.school_mate.model.Teacher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SchoolClassResponseDto {
    private Long id;
    private String name;
    private Set<TeacherResponseDto> teachers;
}
