package lk.sasax.GreenShadow.entity;

import jakarta.persistence.*;
import lk.sasax.GreenShadow.util.Enum.UserRole;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class MonitoringLogService {
    @Id
    private String logCode;
    private LocalDate logDate;
    private String logDetails;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @ManyToOne
    @JoinColumn(name = "field_code", nullable = false)
    private Field  field;
    @OneToMany(mappedBy = "logService")
    private List<CropDetails> cropDetails = new ArrayList<>();
}
