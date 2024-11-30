package lk.sasax.GreenShadow.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Monitoring_Pk implements Serializable {
    private String logCode;

    private String crop_code;

    private String staff_id;
}
