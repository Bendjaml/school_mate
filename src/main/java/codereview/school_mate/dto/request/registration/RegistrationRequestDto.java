package codereview.school_mate.dto.request.registration;

import lombok.Data;

@Data
public class RegistrationRequestDto {
    private String username;
    private String password;
    private String role;
}
