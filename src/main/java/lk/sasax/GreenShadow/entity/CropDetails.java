package lk.sasax.GreenShadow.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@IdClass(Monitoring_Pk.class)
public class CropDetails {


    @Id
    private String logCode;
    @Id
    private String crop_code;
    @Id
    private String staff_id;

    private int quantity;

    private int membersInStaff;



    @ManyToOne
    @JoinColumn(name = "logCode",referencedColumnName = "logCode",insertable = false,updatable = false)
    private MonitoringLogService logService;

    //Out-verse
    @ManyToOne
    @JoinColumn(name = "staff_id",insertable = false,updatable = false)
    private Staff staff;



    @ManyToOne
    @JoinColumn(name = "crop_code",insertable = false,updatable = false)
    private Crop crop;



}
