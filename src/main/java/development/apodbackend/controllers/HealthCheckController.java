package development.apodbackend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/noauth/health-check")
public class HealthCheckController {
    @GetMapping
    public ResponseEntity<Boolean> get() {
        try {
            log.info("RECIVING health-check");
            return ResponseEntity.ok(true);
        }

        catch (Exception Error) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
