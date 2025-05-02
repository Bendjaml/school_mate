package codereview.school_mate.dto;
import codereview.school_mate.model.SchoolClass;
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
    private SchoolClass subject;
    private SchoolClass schoolClass;

}
