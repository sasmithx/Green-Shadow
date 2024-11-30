package lk.sasax.GreenShadow.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/health")
@CrossOrigin(origins = "*")
public class HealthTestController {
    private static final Logger logger = LoggerFactory.getLogger(HealthTestController.class);

    @GetMapping
    public String testHealth(){
        logger.info("Health Test Success , Green Shadow App Run Successfully");
        return "Health Test Success , Green Shadow App Run Successfully";
    }
}
