package lk.sasax.GreenShadow.controller;

import lk.sasax.GreenShadow.service.EquipmentService;
import lk.sasax.GreenShadow.service.FieldService;
import lk.sasax.GreenShadow.service.StaffService;
import lk.sasax.GreenShadow.service.VehicleServie;
import lk.sasax.GreenShadow.service.impl.CropServiceIMPL;
import lk.sasax.GreenShadow.service.impl.UserServiceIMPL;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dash")
@CrossOrigin(origins = "*")
public class DashController {
    private VehicleServie vehicleServie;
    private EquipmentService equipmentService;
    private CropServiceIMPL cropService;
    private FieldService fieldService;
    private StaffService staffService;
    private UserServiceIMPL userService;

    public DashController(VehicleServie vehicleServie, EquipmentService equipmentService,
                          CropServiceIMPL cropService, FieldService fieldService,
                          StaffService staffService,UserServiceIMPL userService) {
        this.vehicleServie = vehicleServie;
        this.equipmentService = equipmentService;
        this.cropService = cropService;
        this.fieldService = fieldService;
        this.staffService = staffService;
        this.userService = userService;
    }

    @GetMapping("/vehicle_count")
    public long getVehicleCount(){
        return vehicleServie.getVehicleCount();
    }

    @GetMapping("/equipment_count")
    public long getEquipmentCount(){
        return equipmentService.getEquipmentCount();
    }

    @GetMapping("/crop_count")
    public long getCropCount(){
        return cropService.getCropCount();
    }

    @GetMapping("/field_count")
    public long getFieldCount(){
        return fieldService.getFieldCount();
    }

    @GetMapping("/staff_count")
    public long getStaffCount(){
        return staffService.getStaffCount();
    }

    @GetMapping("/user_count")
    public long getUserCount(){
        return userService.getUserCount();
    }
}



