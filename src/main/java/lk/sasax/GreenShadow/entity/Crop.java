package lk.sasax.GreenShadow.entity;

import jakarta.persistence.*;
import lk.sasax.GreenShadow.util.Enum.CropCategory;
import lk.sasax.GreenShadow.util.Enum.CropComnName;
import lk.sasax.GreenShadow.util.Enum.CropScienceName;
import lk.sasax.GreenShadow.util.Enum.CropSesasons;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "crops")
public class Crop {
    @Id
    @Column(name = "crop_code")
    private String cropCode;

    @Enumerated(EnumType.STRING)
    private CropComnName cropCommonName;

    @Enumerated(EnumType.STRING)
    private CropScienceName cropScientificName;

    @Column(name = "crop_image" , columnDefinition = "LONGTEXT")
    private String cropImage;

    @Enumerated(EnumType.STRING)
    private CropCategory category;

    private int qty;

    @Enumerated(EnumType.STRING)
    private CropSesasons cropSeason;

    private String fieldCodes;

    private String filedNames;



    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy =  "crops")
    private List<Field> filed = new ArrayList<>();





}
