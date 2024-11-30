package lk.sasax.GreenShadow.entity;

import jakarta.persistence.*;
import lk.sasax.GreenShadow.util.Enum.Locations;
import lombok.Data;

@Data
@Entity
@Table(name = "fields")
public class Field {
    @Id
    @Column(name = "field_code", unique = true, nullable = false)
    private String fieldCode;

    private String fieldName;

    @Enumerated(EnumType.STRING)
    private Locations fieldLocation;

    private Double size;

    @ManyToOne
    @JoinColumn(name = "crop_code")
    private Crop crops;

    private String nameOfCrop;

    @ManyToOne
    @JoinColumn(name = "staff_id" )// This will not create a separate table
    private Staff staff;



    @Column(name = "field_image1" , columnDefinition = "LONGTEXT")
    private String fieldImage1;




}
