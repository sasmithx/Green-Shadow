package lk.sasax.GreenShadow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EquipmentDTO {
    private String equipmentId;
    private String name;
    private String type;
    private String status;
    private int equantity;
    private String assignedStaffId;
    private String assignedFieldCode;
}
