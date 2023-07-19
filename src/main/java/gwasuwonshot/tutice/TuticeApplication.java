package gwasuwonshot.tutice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TuticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TuticeApplication.class, args);
	}

}
