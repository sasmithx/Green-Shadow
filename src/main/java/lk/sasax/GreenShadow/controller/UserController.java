package lk.sasax.GreenShadow.controller;

import lk.sasax.GreenShadow.dto.ResponseDTO;
import lk.sasax.GreenShadow.service.impl.AuthServiceIMPL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")

public class UserController {
    @Autowired
    private AuthServiceIMPL authService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO> signUp(@RequestBody ResponseDTO signUpRequest){
        logger.info("signUp request received");
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }
    @PostMapping("/signin")
    public ResponseEntity<ResponseDTO> signIn(@RequestBody ResponseDTO signInRequest){
        logger.info("signIn request received");
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }
    @PostMapping("/refresh")
    public ResponseEntity<ResponseDTO> refreshToken(@RequestBody ResponseDTO refreshTokenRequest){
        logger.info("refresh token request received");
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }
}
