package telran.ashkelon2018.mishpahug.controller;

import java.security.Principal;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import springfox.documentation.spi.service.contexts.SecurityContext;
import telran.ashkelon2018.mishpahug.configuration.AccountConfiguration;
import telran.ashkelon2018.mishpahug.configuration.AccountUserCredentials;
import telran.ashkelon2018.mishpahug.dto.StaticFieldsDto;
import telran.ashkelon2018.mishpahug.dto.UserProfileDto;
import telran.ashkelon2018.mishpahug.service.AccountService;

@RestController
@RequestMapping("/user") // all will be start from user
@CrossOrigin(origins="*", allowedHeaders="*")
public class AccountManagementController {
	@Autowired
	AccountService accountService;

	@Autowired
	AccountConfiguration accountConfiguration;
	  
	// Authorized requests

	@PostMapping("/registration")
	public UserProfileDto register(@RequestHeader(value = "Authorization") String token) {
		System.out.println("Token= " + token);

		// * add a token to the http session in order to check it on other endpoints *//
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
		HttpSession httpSession = attributes.getRequest().getSession(true);
		System.out.println("UserRegSessionId: "+httpSession.getId());
		httpSession.setAttribute("U_TOKEN", token);

		return accountService.addUser(token);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/profile")
	// @RequestMapping(value="/profile", method = RequestMethod.POST)
	// @PostMapping(value = "/profile", consumes = "application/json;charset=UTF-8")
	public UserProfileDto updateUserProfile(@RequestBody UserProfileDto userProfileDto) {
		System.out.println("userProfileDto.getFirstName= "	+ userProfileDto.getFirstName());
		String sessionLlogin = sessionUserName();	
		System.out.println(sessionLlogin+" --- "+userProfileDto);
		// System.out.println("principal.getName= " + principal.getName());
		return accountService.editUserProfile(userProfileDto, sessionLlogin);
	}

	private String sessionUserName() {
		// * get token from the http session  *//
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
//		HttpServletRequest request = attributes.getRequest();
//		Principal principal = request.getUserPrincipal();
//		System.out.println("principal.getName= " + principal.getName());
		HttpSession httpSession = attributes.getRequest().getSession(true);
		System.out.println("ProfRegSessionId: "+httpSession.getId());
		Object sessionAttribute = httpSession.getAttribute("U_TOKEN");
		if (sessionAttribute == null) {
			return null;
		}
		// String sessionToken = sessionAttribute.toString();
		AccountUserCredentials sessionUser = accountConfiguration.tokenDecode(sessionAttribute.toString());
		System.out.println("User= " + sessionUser.getEmail());
		return sessionUser.getEmail();
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/profile")
	public UserProfileDto getUser(@RequestBody UserProfileDto userProfileDto,
			@RequestHeader("Authorization") String token) {
		return accountService.getUserProfile(userProfileDto, token);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/login")
	public UserProfileDto loginUser(
			@RequestHeader("Authorization") String token) {
		return accountService.login(token);
	}

	// Unauthorized requests

	@GetMapping("/staticfields")
	public StaticFieldsDto staticFields(
			@RequestBody StaticFieldsDto staticFieldsDto) {
		return accountService.getStaticFields(staticFieldsDto);

	}
}