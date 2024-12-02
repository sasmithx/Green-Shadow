package lk.sasax.GreenShadow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$\n",message = "Invalid Email")
    private String  to;
    @Pattern(regexp = ".*",message = "Invalid Subject")
    private String subject;
    @Pattern(regexp = ".*",message = "Invalid Body")
    private String  body;
}

