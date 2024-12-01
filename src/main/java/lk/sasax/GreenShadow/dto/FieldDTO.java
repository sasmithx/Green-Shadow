package lk.sasax.GreenShadow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldDTO {
    @Pattern(regexp = "^FI-\\d{3}$\n",message = "Invalid Code")
    private String fieldCode;
    @Pattern(regexp = "^[A-Za-z]+(?: [A-Za-z]+)*$\n",message = "Invalid Name")
    private String fieldName;
    @Pattern(regexp = "^[A-Z0-9]{2,6}$\n",message = "Invalid Location")
    private String fieldLocation;
    @Pattern(regexp = "^-?\\d+(\\.\\d+)?$\n",message = "Invalid Size")
    private Double size;
    @Pattern(regexp = "^CR-\\d{3}$\n",message = "Invalid Crop Code")
    private String cropCode;
    @Pattern(regexp = "^[A-Za-z]+(?: [A-Za-z]+)*$\n",message = "Invalid Name")
    private String nameOfCrop;
    @Pattern(regexp = "^ST-\\d{3}$\n",message = "Invalid Staff Id")
    private String staffId;
    @NotBlank(message = "Field Image is mandatory")
    private String fieldImage1;
}
