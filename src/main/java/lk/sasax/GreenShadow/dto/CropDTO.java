package lk.sasax.GreenShadow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CropDTO {
    private String cropCode;  // Corresponds to the @Id field in Crop entity
    private String cropCommonName;
    private String cropScientificName;
    private MultipartFile cropImage;
    private String category;
    private int qty;
    private String cropSeason;
    private String fieldCodes;
    private String filedNames;
}
