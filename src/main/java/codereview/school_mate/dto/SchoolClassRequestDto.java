package codereview.school_mate.dto;

import codereview.school_mate.model.Teacher;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolClassRequestDto {

    @NotBlank(message = "Name cannot be empty")
    private String name;
    private Set<TeacherResponseDto> teachers;
}
