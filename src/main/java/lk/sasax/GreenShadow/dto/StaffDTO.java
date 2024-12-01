package lk.sasax.GreenShadow.dto;

import jakarta.validation.constraints.Pattern;
import lk.sasax.GreenShadow.util.Enum.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffDTO {
    @Pattern(regexp = "^ST-\\d{3}$\n",message = "Invalid Staff Id")
    private String staffId;
    @Pattern(regexp = "^[A-Za-z]+(?: [A-Za-z]+)*$\n",message = "Invalid First Name")
    private String firstName;
    @Pattern(regexp = "^[A-Za-z]+(?: [A-Za-z]+)*$\n",message = "Invalid Last Name")
    private String lastName;
    @Pattern(regexp = "^(AGRONOMIST|ADMINISTRATIVE|SCIENTIST|ENGINEER|TECHNICIAN|SUPERVISOR|ANALYST|CONSULTANT|INTERN|DIRECTOR|EXECUTIVE|OFFICER|ASSISTANT|COORDINATOR)$\n",message = "Invalid Designation")
    private String designation;
    @Pattern(regexp = "^(MALE|FEMALE|OTHER)$\n",message = "Invalid Gender Type")
    private Gender gender;
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$\n",message = "Invalid Joined Date")
    private LocalDate joinedDate;
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$\n",message = "Invalid Birth Date")
    private LocalDate dob;
    @Pattern(regexp = "^[A-Za-z]+$\n",message = "Invalid Address Line1")
    private String addressLine1;
    @Pattern(regexp = "^[A-Za-z]+$\n",message = "Invalid Address Line2")
    private String addressLine2;
    @Pattern(regexp = "^[A-Za-z]+$\n",message = "Invalid Address Line3")
    private String addressLine3;
    @Pattern(regexp = "^[A-Za-z]+$\n",message = "Invalid Address Line4")
    private String addressLine4;
    @Pattern(regexp = "^[A-Za-z]+$\n",message = "Invalid Address Line5")
    private String addressLine5;
    @Pattern(regexp = "^\\+?\\d{1,3}?[-.\\s]?\\(?\\d{1,4}?\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}$\n",message = "Invalid Contact Number")
    private String contactNo;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$\n",message = "Invalid Email")
    private String email;
    @Pattern(regexp = "^[1-9]\\d*$\n",message = "Invalid Member Count")
    private int members;
    @Pattern(regexp = "^ST-\\d{3}$\n",message = "Invalid Field Code")
    private String fieldCode;
    @Pattern(regexp = "^VE-\\d{3}$\n",message = "Invalid Vehicle Code")
    private String vCode;
}
