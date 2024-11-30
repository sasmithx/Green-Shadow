package lk.sasax.GreenShadow.dto;

import jakarta.validation.constraints.NotBlank;
import lk.sasax.GreenShadow.util.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    @NotBlank(message = "Email Cannot Be Null")
    private String email;
    @NotBlank(message = "Password Cannot Be Null")
    private String password;
    private Role role;
}