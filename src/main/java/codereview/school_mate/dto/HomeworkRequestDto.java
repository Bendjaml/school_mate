package codereview.school_mate.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class HomeworkRequestDto {
    @NotNull(message = "Дата обязательна")
    @Future(message = "Дата выполнения домашнего задания должны быть в будущем времени")
    private LocalDateTime date;

    @NotBlank(message = "Описание не может быть пустым")
    @Size(min = 10, max = 1000, message = "Описание должно быть от 10 до 1000 символов")
    private String description;

    @NotNull(message = "ID предмета обязателен")
    private Long subjectId;

    @NotNull(message = "ID класса обязателен")
    private Long classId;
}
