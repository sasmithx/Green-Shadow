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
    @Pattern(regexp = "^(RICE|COWPEA|GREENGRAM|CHIKPEA|SWEETPOTATO|CASSAVA|REDDISH)$\n",message = "Invalid Common Name")
    private String cropCommonName;
    @Pattern(regexp = "^(BG34|BW355|CP10|CP90|MI60|MI80|CP2|SP1|RD10|CS5|CS40)$\n", message = "Invalid Scientific Name")
    private String cropScientificName;
    @NotBlank(message = "Crop Image is mandatory")
    private MultipartFile cropImage;
    @Pattern(regexp = "/^(FEED|FIBER|OIL|CIREAL)$/i\n")
    private String category;
    @Pattern(regexp = "^[1-9]\\d*$\n")
    private int qty;
    @Pattern(regexp = "^(JANUARY|FEBRUARY|MARCH|APRIL|MAY|JUNE|JULY|AUGUST|SEPTEMBER|OCTOBER|NOVEMBER|DECEMBER)$\n")
    private String cropSeason;
    @Pattern(regexp = "^FI-\\d{3}$\n")
    private String fieldCodes;
    @Pattern(regexp = "^[A-Za-z]+$\n")
    private String filedNames;
}
