package lk.sasax.GreenShadow.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {
    private int status;
    private String message;
    private final LocalDateTime timestamp = LocalDateTime.now();
    private String token;
    private String role;
    private String  expirationTime;
    private int totalPage;
    private long totalElement;
    private UserDTO user;
    private List<UserDTO> userList;

}
