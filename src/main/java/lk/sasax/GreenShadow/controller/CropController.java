package lk.sasax.GreenShadow.controller;

import jakarta.persistence.EntityNotFoundException;
import lk.sasax.GreenShadow.dto.CropDTO;
import lk.sasax.GreenShadow.entity.Crop;
import lk.sasax.GreenShadow.service.impl.CropServiceIMPL;
import lk.sasax.GreenShadow.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v2/crop")
//@RequiredArgsConstructor
@CrossOrigin
public class CropController {


    @Autowired
    private CropServiceIMPL cropService;

    private static final Logger logger = LoggerFactory.getLogger(CropController.class);

    @GetMapping
    public ResponseEntity<List<CropDTO>> getAllCrops() {
        List<CropDTO> crops = cropService.getAllCrops();
        logger.info("Crops found successfully");
        return ResponseEntity.ok(crops);
    }

    @PostMapping
    public ResponseEntity<Crop> saveCrop(
            @RequestParam("cropCommonName") String cropCommonName,
            @RequestParam("cropScientificName") String cropScientificName,
            @RequestParam("category") String category,
            @RequestParam("qty") int qty,
            @RequestParam("cropSeason") String cropSeason,
            @RequestParam("fieldCodes") String fieldCodes,
            @RequestParam("filedNames") String filedNames,
            @RequestParam(value = "cropImage") MultipartFile cropImage) throws IOException, IOException {

        CropDTO cropDTO = new CropDTO();
        cropDTO.setCropCommonName(cropCommonName);
        cropDTO.setCropScientificName(cropScientificName);
        cropDTO.setCategory(category);
        cropDTO.setQty(qty);
        cropDTO.setCropSeason(cropSeason);
        cropDTO.setFieldCodes(fieldCodes);
        cropDTO.setFiledNames(filedNames);
        cropDTO.setCropImage(cropImage);

        Crop savedCrop = cropService.saveCrop(cropDTO);
        logger.info("Crop saved successfully");
        return ResponseEntity.ok(savedCrop);
    }


    @PutMapping("/{cropCode}")
    public ResponseEntity<Crop> updateCrop(
            @PathVariable("cropCode") String cropCode,
            @RequestParam("cropCommonName") String cropCommonName,
            @RequestParam("cropScientificName") String cropScientificName,
            @RequestParam("category") String category,
            @RequestParam("qty") int qty,
            @RequestParam("cropSeason") String cropSeason,
            @RequestParam("fieldCodes") String fieldCodes,
            @RequestParam("filedNames") String filedNames,
            @RequestPart(value = "cropImage", required = false) MultipartFile cropImage
    ) {
        try {
            CropDTO cropDTO = new CropDTO();
            cropDTO.setCropCommonName(cropCommonName);
            cropDTO.setCropScientificName(cropScientificName);
            cropDTO.setCategory(category);
            cropDTO.setQty(qty);
            cropDTO.setCropSeason(cropSeason);
            cropDTO.setFieldCodes(fieldCodes);
            cropDTO.setFiledNames(filedNames);

            if (cropImage != null && !cropImage.isEmpty()) {
                cropDTO.setCropImage(cropImage);
            }

            Crop updatedCrop = cropService.updateCrop(cropCode, cropDTO);
            logger.info("Crop updated successfully with code: {}", cropCode);
            return ResponseEntity.ok(updatedCrop);
        } catch (EntityNotFoundException ex) {
            logger.error("Crop update failed - Crop with code {} not found", cropCode);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IOException ex) {
            logger.error("Unexpected error occurred during crop update for crop with code {}", cropCode);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception ex) {
            logger.error("Unexpected error occurred during crop update for crop with code {}", cropCode);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @DeleteMapping
    public ResponseUtil deleteCrop(@RequestParam("cCode") String cCode){
        cropService.deleteCrop(cCode);
        logger.info("Crop deleted successfully");
        return new ResponseUtil(200,"Deleted",null);
    }


    @GetMapping("/cropCode")
    public ResponseEntity<Map<String, String>> generateCropCode() {
        String newFieldCode = cropService.generateCropCode();
        Map<String, String> response = new HashMap<>();
        response.put("cropCode", newFieldCode);
        logger.info("Crop generated successfully");
        return ResponseEntity.ok(response);
    }

}
