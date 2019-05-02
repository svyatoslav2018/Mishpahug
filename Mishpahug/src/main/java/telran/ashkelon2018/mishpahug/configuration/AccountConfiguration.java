package telran.ashkelon2018.mishpahug.configuration;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ManagedResource
public class AccountConfiguration {

	@Value("${exp.value}")
	int expPeriod;
	
	@ManagedAttribute
	public int getExpPeriod() {
		return expPeriod;
	}

	@ManagedAttribute
	public void setExpPeriod(int expPeriod) {
		this.expPeriod = expPeriod;
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
	
	
	public AccountUserCredentials tokenDecode(String token) {
		try {
			int index = token.indexOf(" ");
			token = token.substring(index + 1);
			byte[] base64DecodeBytes = Base64.getDecoder().decode(token);
			token = new String(base64DecodeBytes);
			String[] auth = token.split(":");

			auth[0] = auth[0].toLowerCase().replaceAll("\\.", "");
			AccountUserCredentials credentials = new AccountUserCredentials((auth[0]), auth[1]);

			return credentials;
		} catch (Exception e) {
			// token may be empty
			e.printStackTrace();
			return null;
		}

	}
}
