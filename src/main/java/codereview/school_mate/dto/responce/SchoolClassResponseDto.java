package codereview.school_mate.dto.responce;

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
