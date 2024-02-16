package development.apodbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableScheduling
@CrossOrigin(origins = "*")
public class ApodBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApodBackendApplication.class, args);
	}

}
