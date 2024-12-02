package lk.sasax.GreenShadow.controller;

import lk.sasax.GreenShadow.dto.FieldDTO;
import lk.sasax.GreenShadow.service.impl.FieldServiceIMPL;
import lk.sasax.GreenShadow.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/field")
@CrossOrigin
public class FieldController {

    @Autowired
    FieldServiceIMPL fieldServiceIMPL;

    private static final Logger logger = LoggerFactory.getLogger(FieldController.class);

    @PostMapping("/save")
    public ResponseEntity<FieldDTO> saveField(
            @RequestParam("fieldName") String fieldName,
            @RequestParam("fieldLocation") String fieldLocation,
            @RequestParam("size") Double size,
            @RequestParam(value = "cropCode") String cropCode,
            @RequestParam(value = "nameOfCrop") String nameOfCrop,
            @RequestParam(value = "staffId") String staffId,
            @RequestParam(value = "fieldImageFile", required = false) MultipartFile fieldImageFile
    ) {
        try {
            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setFieldName(fieldName);
            fieldDTO.setFieldLocation(fieldLocation);
            fieldDTO.setSize(size);
            fieldDTO.setCropCode(cropCode);
            fieldDTO.setNameOfCrop(nameOfCrop);
            fieldDTO.setStaffId(staffId);

            FieldDTO savedField = fieldServiceIMPL.saveField(fieldDTO, fieldImageFile);
            logger.info("Field saved successfully");
            return new ResponseEntity<>(savedField, HttpStatus.CREATED);
        } catch (IOException e) {
            logger.error("Error while saving field", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<FieldDTO> updateField(
            @RequestParam("fieldCode") String fieldCode,
            @RequestParam("fieldName") String fieldName,
            @RequestParam("fieldLocation") String fieldLocation,
            @RequestParam("size") Double size,
            @RequestParam(value = "cropCode", required = false) String cropCode,
            @RequestParam(value = "nameOfCrop", required = false) String nameOfCrop,
            @RequestParam(value = "staffId", required = false) String staffId,
            @RequestParam(value = "fieldImageFile", required = false) MultipartFile fieldImageFile
    ) throws IOException {

        FieldDTO fieldDTO = new FieldDTO();
        fieldDTO.setFieldName(fieldName);
        fieldDTO.setFieldLocation(fieldLocation);
        fieldDTO.setSize(size);
        fieldDTO.setCropCode(cropCode);
        fieldDTO.setNameOfCrop(nameOfCrop);
        fieldDTO.setStaffId(staffId);

        FieldDTO updatedField = fieldServiceIMPL.updateField(fieldCode, fieldDTO, fieldImageFile);
        logger.info("Field updated successfully");

        return ResponseEntity.ok(updatedField);
    }

    @DeleteMapping
    public ResponseUtil deleteField(@RequestParam("fCode") String fCode){
        fieldServiceIMPL.deleteFiled(fCode);
        logger.info("Field deleted successfully");
        return new ResponseUtil(200,"Deleted",null);

    }

    @GetMapping
    public ResponseUtil getAllFields() {
        logger.info("Get all fields successfully");
        return new ResponseUtil(200, "OK", fieldServiceIMPL.getAllFields());
    }

    @GetMapping("/generateFieldCode")
    public ResponseEntity<String> generateFieldCode() {
        String newFieldCode = fieldServiceIMPL.generateFieldCode();
        logger.info("New field code generated successfully");
        return ResponseEntity.ok(newFieldCode);
    }
}
