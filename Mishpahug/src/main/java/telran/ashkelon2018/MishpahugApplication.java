package telran.ashkelon2018;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import telran.ashkelon2018.mishpahug.dao.UserAccountRepository;
import telran.ashkelon2018.mishpahug.domain.UserAccount;

//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@SpringBootApplication
public class MishpahugApplication {
	@Autowired
	UserAccountRepository repository;
	@Autowired
	PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(MishpahugApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		if (!repository.existsById("user")) {
//			String hashPassword = encoder.encode("user");
//			UserAccount userAccount = UserAccount.builder().login("user")
//					.password(hashPassword).firstName("user").lastName("user")
//					.standartrole("user").build();
//			repository.save(userAccount);
//		}
//
//	}

}
