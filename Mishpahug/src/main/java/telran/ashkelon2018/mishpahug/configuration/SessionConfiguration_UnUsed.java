package telran.ashkelon2018.mishpahug.configuration;
//package telran.ashkelon2018.mishpahug.configuration;
//
//import java.util.Base64;
//
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//@Configuration
//
//public class SessionConfiguration {
//	
//	@Autowired
//	AccountConfiguration accountConfiguration;
//	
//	public String sessionUserName() {
//		// * get token from the http session *//
//		ServletRequestAttributes attributes = (ServletRequestAttributes) 
//			    RequestContextHolder.currentRequestAttributes();
//		System.out.println("!!!!!!!!! sessionUserName, getSessionId " + attributes.getSessionId());
//		HttpSession httpSession = attributes.getRequest().getSession(true);
//		Object sessionAttribute = httpSession.getAttribute("U_TOKEN");
//		System.out.println("!!!!!!!!! sessionUserName, sessionAttribute U_TOKEN " + sessionAttribute);
//		if (sessionAttribute == null) {
//			return null;
//		}
//		
//		//AccountUserCredentials sessionUser = accountConfiguration.tokenDecode(sessionAttribute.toString());
//		//AccountUserCredentials sessionUser = tokenDecode(sessionAttribute.toString());
//		//return sessionUser.getLogin();
//		
//		return sessionAttribute.toString();
//	}
//
//	public void setAttributeToken(String token) {
//		ServletRequestAttributes attributes = (ServletRequestAttributes) 
//			    RequestContextHolder.currentRequestAttributes();
//		HttpSession httpSession = attributes.getRequest().getSession(true);
//		System.out.println("UserRegSessionId: " + httpSession.getId());
//		System.out.println("U_TOKEN: " + token);
//		httpSession.setAttribute("U_TOKEN", token);
//		
//	}
//	
//	public void invalidateToken() {
//		ServletRequestAttributes attributes = (ServletRequestAttributes) 
//			    RequestContextHolder.currentRequestAttributes();
//		HttpSession httpSession = attributes.getRequest().getSession(true);
//		System.out.println("InvalidateSessionId: " + httpSession.getId());
////		System.out.println("U_TOKEN: " + token);
////		httpSession.setAttribute("U_TOKEN", token);
//		httpSession.invalidate();
//	}
//	
////	public AccountUserCredentials tokenDecode(String token) {
////		int index = token.indexOf(" ");
////		token = token.substring(index + 1);
////		byte[] base64DecodeBytes = Base64.getDecoder().decode(token);
////		token = new String(base64DecodeBytes);
////		String[] auth = token.split(":");
////		auth[0] = auth[0].toLowerCase().replaceAll("\\.", "");
////		AccountUserCredentials credentials = new AccountUserCredentials((auth[0]), auth[1]);
////		return credentials;
////
////	}
//	
//
//}
