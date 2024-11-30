package lk.sasax.GreenShadow.dto;

import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldDTO {
    @Null(message = "field CODE is auto genarated")
    private String fieldCode;
    private String fieldName;
    private String fieldLocation;
    private Double size;
    private String cropCode;
    private String nameOfCrop;
    private String staffId;
    private String fieldImage1;  // File upload for fieldImage1

}
