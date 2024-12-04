package lk.sasax.GreenShadow.controller;

import jakarta.persistence.EntityNotFoundException;
import lk.sasax.GreenShadow.dto.CropDetailDTO;
import lk.sasax.GreenShadow.service.CropDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/crop_detail")
@CrossOrigin(origins = "*")
public class CropDetailController {
    private final CropDetailService cropDetailService;

    @Autowired
    public CropDetailController(CropDetailService cropDetailService) {
        this.cropDetailService = cropDetailService;
    }

    @PostMapping
    public ResponseEntity<CropDetailDTO> saveCropDetail(@RequestBody CropDetailDTO cropDetailDTO) {
        try {
            CropDetailDTO savedCropDetail = cropDetailService.saveCropDetail(cropDetailDTO);
            return new ResponseEntity<>(savedCropDetail, HttpStatus.CREATED);
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

