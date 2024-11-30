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
    private String  to;
    @NotBlank(message = "Subject or email title is required")
    private String subject;
    @NotBlank(message = "Body of emial is required")
    private String  body;
}