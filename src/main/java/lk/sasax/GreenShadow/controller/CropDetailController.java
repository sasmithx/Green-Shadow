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
@CrossOrigin
public class CropDetailController {
    private final CropDetailService cropDetailService;

    @Autowired
    public CropDetailController(CropDetailService cropDetailService) {
        this.cropDetailService = cropDetailService;
    }

    @PostMapping
    public ResponseEntity<CropDetailDTO> saveCropDetail(@RequestBody CropDetailDTO cropDetailDTO) {
        try {
            // Calling the service to save crop details
            CropDetailDTO savedCropDetail = cropDetailService.saveCropDetail(cropDetailDTO);
            return new ResponseEntity<>(savedCropDetail, HttpStatus.CREATED);  // Return status 201 for successful creation
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            // Handle validation and entity not found exceptions
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // Return 400 for bad requests
        } catch (Exception e) {
            // Generic exception handler
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  // Return 500 for server errors
        }
    }
}

