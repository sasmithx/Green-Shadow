package lk.sasax.GreenShadow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CropDetailDTO {
    private String logCode;
    private String cropCode;
    private String staffId;
    private int quantity;
    private int membersInStaff;
}
