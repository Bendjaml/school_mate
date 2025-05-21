package codereview.school_mate.dto;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequestDto {
    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
    private String name;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Size(min = 2, max = 50, message = "Фамилия должна быть от 2 до 50 символов")
    private String surname;

    @Size(max = 50, message = "Отчество не должно превышать 50 символов")
    private String patronymic;

    @NotNull(message = "ID класса обязательно")
    @Min(1)
    private Long schoolClassId;

    @NotNull(message = "ID класса обязательно")
    @Min(1)
    private Long parentId;
}