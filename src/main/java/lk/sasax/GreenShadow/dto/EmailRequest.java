package lk.sasax.GreenShadow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {
    @NotBlank(message = "Recipient is required")
    private String  to; //Recipientemail to send to
    @NotBlank(message = "Subject or email title is required")
    private String subject; //title
    @NotBlank(message = "Body of emial is required")
    private String  body; //body text
}
