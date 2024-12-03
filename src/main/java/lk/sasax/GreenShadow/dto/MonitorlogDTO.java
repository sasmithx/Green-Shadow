package lk.sasax.GreenShadow.dto;

import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MonitorlogDTO {
    @Pattern(regexp = "^LOG-\\d{3}$\n",message = "Invalid Log Code")
    private String logCode;
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$\n",message = "Invalid Date")
    private LocalDate logDate;
    @Pattern(regexp = "^[A-Za-z]+(?: [A-Za-z]+)*$\n",message = "Invalid Detail Format")
    private String logDetails;
    @Pattern(regexp = "^[A-Za-z]+(?: [A-Za-z]+)*$\n",message = "Invalid Role")
    private String role;
    @Pattern(regexp = "^FI-\\d{3}$\n",message = "Invalid Field Code")
    private String fieldCode;
    private List<CropDetailDTO> cropDetails;
}
