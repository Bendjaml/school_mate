package codereview.school_mate.dto.request.registration;

import lombok.Data;

@Data
public class TeacherRegistrationRequestDto {
    private String username;
    private String password;
    private String role;
    private String name;
    private String surname;
    private String patronymic;
}
