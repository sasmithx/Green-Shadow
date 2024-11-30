package lk.sasax.GreenShadow.controller;

import lk.sasax.GreenShadow.dto.ReqRespDTO;
import lk.sasax.GreenShadow.service.impl.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2/auth")

public class UserController {
    @Autowired
    private AuthService authService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/signup")
    public ResponseEntity<ReqRespDTO> signUp(@RequestBody ReqRespDTO signUpRequest){
        logger.info("signUp request received");
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }
    @PostMapping("/signin")
    public ResponseEntity<ReqRespDTO> signIn(@RequestBody ReqRespDTO signInRequest){
        logger.info("signIn request received");
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }
    @PostMapping("/refresh")
    public ResponseEntity<ReqRespDTO> refreshToken(@RequestBody ReqRespDTO refreshTokenRequest){
        logger.info("refresh token request received");
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }
}
