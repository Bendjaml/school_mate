package codereview.school_mate.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentResponseDto {
    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private String contacts;
    private List<StudentResponseDto> children= new ArrayList<>();
}
