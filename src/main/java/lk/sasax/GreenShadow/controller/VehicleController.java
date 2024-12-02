package lk.sasax.GreenShadow.controller;

import lk.sasax.GreenShadow.dto.VehicleDTO;
import lk.sasax.GreenShadow.exception.NotFoundException;
import lk.sasax.GreenShadow.service.impl.VehicleServiceIMPL;
import lk.sasax.GreenShadow.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "api/v1/vehicles")
@CrossOrigin("*")
public class VehicleController {

    @Autowired
    VehicleServiceIMPL vechielServiceIMPL;

    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @PostMapping
    public ResponseEntity<VehicleDTO> saveVehicle(@RequestBody VehicleDTO vDTO) {
        try {
            VehicleDTO saveVehicle = vechielServiceIMPL.saveVehicle(vDTO);
            logger.info("save Vehicle successfully");
            return new ResponseEntity<>(saveVehicle, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("error while saving vehicle", e);
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<VehicleDTO> updateVehicle(@RequestBody VehicleDTO vDTO) {
        try {
            vechielServiceIMPL.updateVehicle(vDTO);
            logger.info("update Vehicle successfully");
            return new ResponseEntity<>(vDTO, HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("error while updating vehicle", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public  ResponseUtil deleteVehicle(@RequestParam("vCode") String vCode){
        vechielServiceIMPL.deleteVehicle(vCode);
        logger.info("delete Vehicle successfully");
        return new ResponseUtil(200,"Deleted",null);
    }

    @GetMapping
    public ResponseUtil getAlVehicles() {
        logger.info("getAlVehicles successfully");
        return new ResponseUtil(200, "OK", vechielServiceIMPL.getAllVehicles());
    }

    @GetMapping("/nextVd")
    @ResponseStatus(HttpStatus.ACCEPTED)
    String getNextVehicleCode(){
        logger.info("getNextVehicleCode successfully");
        return vechielServiceIMPL.genarateNextVcode();
    }

}
