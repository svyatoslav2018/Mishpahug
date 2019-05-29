package telran.ashkelon2018.mishpahug.configuration;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import telran.ashkelon2018.mishpahug.service.security.jwt.SecurityConstants;

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
		System.out.println("AccountUserCredentials tokenDecode " + token);
		try {
			if (!token.contains("Bearer")) {
				int index = token.indexOf(" ");
				token = token.substring(index + 1);
				byte[] base64DecodeBytes = Base64.getDecoder().decode(token);
				token = new String(base64DecodeBytes);
				String[] auth = token.split(":");
				auth[0] = auth[0].toLowerCase().replaceAll("\\.", "");
				AccountUserCredentials credentials = new AccountUserCredentials(
						(auth[0]), auth[1]);
				return credentials;
			} else {
				Claims claims = Jwts.parser()
						.setSigningKey(SecurityConstants.JWT_SECRET.getBytes())
						.parseClaimsJws(token.replace("Bearer ", "")).getBody();
				String userClaims = claims.getSubject();
				AccountUserCredentials credentials = new AccountUserCredentials(
						userClaims, null);
				return credentials;
			}
		} catch (Exception e) {
			// token may be empty
			e.printStackTrace();
			return null;
		}

	}
}
