package lk.sasax.GreenShadow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CropDTO {
    @Pattern(regexp = "^CR-\\d{3}$\n",message = "Invalid Code")
    private String cropCode;
    @Pattern(regexp = "^(RICE|COWPEA|GREENGRAM|CHIKPEA|SWEETPOTATO|CASSAVA)$\n",message = "Invalid Common Name")
    private String cropCommonName;
    @Pattern(regexp = "^(BG\\d{2}|CP\\d{2}|MI\\d{2}|CH\\d{2}|CS\\d{2})$\n", message = "Invalid Scientific Name")
    private String cropScientificName;
    @NotBlank(message = "Crop Image is mandatory")
    private MultipartFile cropImage;
    @Pattern(regexp = "\\b(FEED|FIBER|OIL|CIREAL)\\b\n",message = "Invalid Category")
    private String category;
    @Pattern(regexp = "^[1-9]\\d*$\n",message = "Invalid Quantity")
    private int qty;
    @Pattern(regexp = "^(JANUARY|FEBRUARY|MARCH|APRIL|MAY|JUNE|JULY|AUGUST|SEPTEMBER|OCTOBER|NOVEMBER|DECEMBER)$\n",message = "Invalid Crop Season")
    private String cropSeason;
    @Pattern(regexp = "^FI-\\d{3}$\n",message = "Invalid Field Code")
    private String fieldCodes;
    @Pattern(regexp = "^[A-Za-z]+$\n",message = "Invalid Field Name")
    private String filedNames;
}
