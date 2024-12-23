package lk.sasax.GreenShadow.controller;

import lk.sasax.GreenShadow.dto.EquipmentDTO;
import lk.sasax.GreenShadow.exception.NotFoundException;
import lk.sasax.GreenShadow.service.EquipmentService;
import lk.sasax.GreenShadow.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/equipment")
@CrossOrigin
public class EquipmentController {

    @Autowired
    EquipmentService equipmentService;

    private static final Logger logger = LoggerFactory.getLogger(EquipmentController.class);

    @PostMapping
    public ResponseEntity<EquipmentDTO> saveEquipment(@RequestBody EquipmentDTO eDTO) {
        try {
            EquipmentDTO savee = equipmentService.saveEquipment(eDTO);
            logger.info("Equipment saved successfully");
            return new ResponseEntity<>(savee, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Can't save equipment", e);
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<EquipmentDTO> updateEquipment(@RequestBody EquipmentDTO eDTO) {
        try {
            equipmentService.updateEquipment(eDTO);
            logger.info("Equipment updated successfully");
            return new ResponseEntity<>(eDTO, HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("Equipment not found", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public  ResponseUtil deleteEquipment(@RequestParam("eCode") String eCode){
        equipmentService.deleteEquipment(eCode);
        logger.info("Equipment deleted successfully");
        return new ResponseUtil(200,"Deleted",null);

    }

    @GetMapping
    public ResponseUtil getAlEquipments() {
        logger.info("Equipments found successfully");
        return new ResponseUtil(200, "OK", equipmentService.getAllEquipments());
    }

    @GetMapping("/nextEId")
    @ResponseStatus(HttpStatus.ACCEPTED)
    String getNextEquipmentCode(){
        logger.info("Next equipment code generated successfully");
        return equipmentService.generateNextEquipmentCode();
    }

}
