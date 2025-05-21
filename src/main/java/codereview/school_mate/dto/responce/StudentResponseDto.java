package codereview.school_mate.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDto {
    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private SchoolClassResponseDto schoolClass;
    private ParentResponseDto parent;
}