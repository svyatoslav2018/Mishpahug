package telran.ashkelon2018.mishpahug.configuration;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration

public class SessionConfiguration {

	public String sessionUserName() {
		// * get token from the http session *//
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
		System.out.println("!!!!!!!!! sessionUserName, getSessionId " + attributes.getSessionId());
		HttpSession httpSession = attributes.getRequest().getSession(true);
		Object sessionAttribute = httpSession.getAttribute("U_TOKEN");
		System.out.println("!!!!!!!!! sessionUserName, sessionAttribute U_TOKEN " + sessionAttribute);
		if (sessionAttribute == null) {
			return null;
		}

		AccountUserCredentials sessionUser = tokenDecode(sessionAttribute.toString());
		return sessionUser.getLogin();
	}

	public void setAttributeToken(String token) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
		HttpSession httpSession = attributes.getRequest().getSession(true);
		System.out.println("UserRegSessionId: " + httpSession.getId());
		System.out.println("U_TOKEN: " + token);
		httpSession.setAttribute("U_TOKEN", token);
		
	}
	
	public void invalidateToken() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
		HttpSession httpSession = attributes.getRequest().getSession(true);
		System.out.println("InvalidateSessionId: " + httpSession.getId());

//		System.out.println("U_TOKEN: " + token);
//		httpSession.setAttribute("U_TOKEN", token);

		httpSession.invalidate();
	}
	



	public AccountUserCredentials tokenDecode(String token) {
		int index = token.indexOf(" ");
		token = token.substring(index + 1);
		byte[] base64DecodeBytes = Base64.getDecoder().decode(token);
		token = new String(base64DecodeBytes);
		String[] auth = token.split(":");
		auth[0] = auth[0].toLowerCase().replaceAll("\\.", "");
		AccountUserCredentials credentials = new AccountUserCredentials((auth[0]), auth[1]);
		return credentials;

	}
	

}
