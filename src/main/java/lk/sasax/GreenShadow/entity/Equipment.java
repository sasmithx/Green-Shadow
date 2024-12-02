package lk.sasax.GreenShadow.entity;

import jakarta.persistence.*;
import lk.sasax.GreenShadow.util.Enum.EquipmentTypes;
import lk.sasax.GreenShadow.util.Enum.Status;
import lombok.Data;

@Entity
@Data
@Table(name = "equipment")
public class Equipment {
    @Id
    private String equipmentId;

    private String name;

    @Enumerated(EnumType.STRING)
    private EquipmentTypes type;

    @Enumerated(EnumType.STRING)
    private Status status;

    private int equntity;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff assignedStaff;

    @ManyToOne
    @JoinColumn(name = "field_code")
    private Field assignedField;
}
