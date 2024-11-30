package lk.sasax.GreenShadow.dto;

import lk.sasax.GreenShadow.util.Enum.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffDTO {
    private String staffId;
    private String firstName;
    private String lastName;
    private String designation;
    private Gender gender;
    private LocalDate joinedDate;
    private LocalDate dob;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    private String contactNo;
    private String email;
    private int members;
    private String fieldCode;
    private String vCode;

}
