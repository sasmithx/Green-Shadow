package lk.sasax.GreenShadow.controller;

import lk.sasax.GreenShadow.dto.StaffDTO;
import lk.sasax.GreenShadow.exception.NotFoundException;
import lk.sasax.GreenShadow.service.impl.StaffServiceIMPL;
import lk.sasax.GreenShadow.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/staff")
@CrossOrigin
public class StaffController {


    @Autowired
    StaffServiceIMPL staffServiceIMPL;

    private static final Logger logger = LoggerFactory.getLogger(StaffController.class);

    @GetMapping
    public ResponseUtil getAllStaffs() {
        logger.info("getAllStaffs successfully");
        return new ResponseUtil(200, "OK", staffServiceIMPL.getAllCrops());
    }


    @PostMapping
    public ResponseEntity<StaffDTO> saveStaff(@RequestBody StaffDTO staffDTO) {
        try {
            StaffDTO savedStaff = staffServiceIMPL.saveStaff(staffDTO);
            logger.info("saveStaff successfully");
            return new ResponseEntity<>(savedStaff, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("saveStaff error", e);
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<StaffDTO> updateStaff(@RequestBody StaffDTO staffDTO) {
        try {
            staffServiceIMPL.updateStaff(staffDTO);
            logger.info("updateStaff successfully");
            return new ResponseEntity<>(staffDTO, HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("updateStaff error", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping
    public  ResponseUtil deleteStaff(@RequestParam("sCode") String sCode){
        staffServiceIMPL.deleteStaff(sCode);
        logger.info("deleteStaff successfully");
        return new ResponseUtil(200,"Deleted",null);

    }


    @GetMapping("/nextId")
    @ResponseStatus(HttpStatus.ACCEPTED)
    String getNextStaffCode(){
        logger.info("getNextStaffCode successfully");
        return staffServiceIMPL.genarateNextStaffCode();
    }
}
