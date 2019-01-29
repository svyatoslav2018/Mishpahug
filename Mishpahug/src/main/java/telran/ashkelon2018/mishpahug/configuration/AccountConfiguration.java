package telran.ashkelon2018.mishpahug.configuration;

import java.util.Base64;

import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.annotation.ManagedResource;

@Configuration
@ManagedResource
public class AccountConfiguration {
	
	public AccountUserCredentials tokenDecode(String token) {

		int index = token.indexOf(" ");
		token = token.substring(index + 1);
		byte[] base64DecodeBytes = Base64.getDecoder().decode(token);
		token = new String(base64DecodeBytes);
		String[] auth = token.split(":");
		AccountUserCredentials credentials = new AccountUserCredentials((auth[0]), auth[1]);

		return credentials;

	}
}
