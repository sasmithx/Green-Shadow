package lk.sasax.GreenShadow.dto;

import jakarta.validation.constraints.Pattern;
import lk.sasax.GreenShadow.util.Enum.Fuel;
import lk.sasax.GreenShadow.util.Enum.Status;
import lk.sasax.GreenShadow.util.Enum.VehicleTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleDTO {
    @Pattern(regexp = "^VE-\\d{3}$\n",message = "Invalid Code")
    private String vehicleCode;
    @Pattern(regexp = "^(CAR|TRU|TRA|VAN|SUV|CRA|ATV)-\\d{4}$\n",message = "Invalid License Plate Number")
    private String licensePlateNumber;
    @Pattern(regexp = "^(CAR|TRUCK|MOTORCYCLE|BUS|VAN|TRACTOR|ATV|SUV|PICKUP|MINIVAN|BICYCLE|TRAILER|FIRE_TRUCK|CRANE)$\n",message = "Invalid Vehicle Category")
    private VehicleTypes vehicleCategory;
    @Pattern(regexp = "^(PETROL|DIESEL)$\n",message = "Invalid Fuel Type")
    private Fuel fuelType;
    @Pattern(regexp = "^(AVAILABLE|NOT)$\n",message = "Invalid Status")
    private Status status;
    @Pattern(regexp = "^ST-\\d{3}$\n",message = "Invalid Staff Id")
    private String allocatedStaffId;
    @Pattern(regexp = "^[A-Za-z]+(?: [A-Za-z]+)*$\n",message = "Invalid Remark")
    private String remarks;
}
