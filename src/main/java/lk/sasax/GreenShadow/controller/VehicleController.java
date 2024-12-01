package lk.sasax.GreenShadow.controller;

import lk.sasax.GreenShadow.dto.VechielDTO;
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


    @GetMapping
    public ResponseUtil getAlVehicles() {
        logger.info("getAlVehicles sucessfully");
        return new ResponseUtil(200, "OK", vechielServiceIMPL.getAllVehecl());
    }


    @PostMapping
    public ResponseEntity<VechielDTO> saveVehicle(@RequestBody VechielDTO vDTO) {
        try {
            VechielDTO saveVehicle = vechielServiceIMPL.saveVehicle(vDTO);
            logger.info("save Vehicle sucessfully");
            return new ResponseEntity<>(saveVehicle, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("error while saving vehicle", e);
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<VechielDTO> updateVehicle(@RequestBody VechielDTO vDTO) {
        try {
            vechielServiceIMPL.updateVehicle(vDTO);
            logger.info("update Vehicle sucessfully");
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


    @GetMapping("/nextVd")
    @ResponseStatus(HttpStatus.ACCEPTED)
    String getNextVehicleCode(){
        logger.info("getNextVehicleCode sucessfully");
        return vechielServiceIMPL.genarateNextVcode();
    }

}
