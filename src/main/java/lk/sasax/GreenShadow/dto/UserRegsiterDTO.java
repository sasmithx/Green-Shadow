package lk.sasax.GreenShadow.dto;


import jakarta.validation.constraints.Pattern;
import lk.sasax.GreenShadow.util.Enum.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRegsiterDTO {

    @Pattern(regexp = "^[A-Za-z]+(?: [A-Za-z]+)*$\n",message = "Invalid Name")
    private String name;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$\n",message = "Invalid Email")
    private String email;

    @Pattern(regexp = "^.*$\n",message = "Invalid Password")
    private String password;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$\n",message = "Invalid Phone Number")
    private String phoneNumber;

    private UserRole role;
}
