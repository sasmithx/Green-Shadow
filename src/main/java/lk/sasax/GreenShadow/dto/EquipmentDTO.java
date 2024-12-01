package lk.sasax.GreenShadow.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EquipmentDTO {
    @Pattern(regexp = "^EQ-\\d{3}$\n",message = "Invalid Code")
    private String equipmentId;
    @Pattern(regexp = "^[A-Za-z]+(?: [A-Za-z]+)*$\n",message = "Invalid Name")
    private String name;
    @Pattern(regexp = "^(ELECTRICAL|MECHANICAL)$\n",message = "Invalid Type")
    private String type;
    @Pattern(regexp = "^(AVAILABLE|NOT)$\n",message = "Invalid Status")
    private String status;
    @Pattern(regexp = "^[1-9]\\d*$\n",message = "Invalid Qunatity")
    private int equantity;
    @Pattern(regexp = "^ST-\\d{3}$\n",message = "Invalid Staff Id")
    private String assignedStaffId;
    @Pattern(regexp = "^FI-\\d{3}$\n",message = "Invalid Field Code")
    private String assignedFieldCode;
}
