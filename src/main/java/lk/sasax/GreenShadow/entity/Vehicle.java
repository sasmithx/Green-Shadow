package lk.sasax.GreenShadow.entity;

import jakarta.persistence.*;
import lk.sasax.GreenShadow.util.Enum.Fuel;
import lk.sasax.GreenShadow.util.Enum.Status;
import lk.sasax.GreenShadow.util.Enum.VehicleTypes;
import lombok.Data;

@Data
@Entity
public class Vehicle {
    @Id
    private String vehicleCode;
    private String licensePlateNumber;
    @Enumerated(EnumType.STRING)
    private VehicleTypes vehicleCategory;
    @Enumerated(EnumType.STRING)
    private Fuel fuelType;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff allocatedStaff;
    private String remarks;
}
