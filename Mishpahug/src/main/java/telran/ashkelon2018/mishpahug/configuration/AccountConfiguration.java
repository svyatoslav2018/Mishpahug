package telran.ashkelon2018.mishpahug.configuration;

import java.util.Base64;

import org.springframework.context.annotation.Configuration;

@Configuration

public class AccountConfiguration {

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
