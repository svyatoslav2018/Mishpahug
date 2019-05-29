package telran.ashkelon2018;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@SpringBootApplication
public class MishpahugApplication {


	public static void main(String[] args) {
		SpringApplication.run(MishpahugApplication.class, args);
	}


}
