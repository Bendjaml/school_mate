package codereview.school_mate.dto.request.registration;

import lombok.Data;

@Data
public class StudentRegistrationRequestDto {
    private String username;
    private String password;
    private String role;
    private String name;
    private String surname;
    private String patronymic;
    private Long schoolClassId;
}
