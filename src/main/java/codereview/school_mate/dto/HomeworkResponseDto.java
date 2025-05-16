package codereview.school_mate.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeworkResponseDto {
    private Long id;
    private LocalDateTime date;
    private String descriptionHomeworks;
    private SubjectResponseDto subject;
    private SchoolClassResponseDto schoolClass;

}
