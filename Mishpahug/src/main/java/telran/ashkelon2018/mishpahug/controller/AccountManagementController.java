package telran.ashkelon2018.mishpahug.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.mishpahug.configuration.AccountConfiguration;
import telran.ashkelon2018.mishpahug.configuration.SessionConfiguration;
import telran.ashkelon2018.mishpahug.dto.StaticFieldsDto;
import telran.ashkelon2018.mishpahug.dto.UserProfileDto;
import telran.ashkelon2018.mishpahug.service.AccountService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/user") // all will be start from user
public class AccountManagementController {
	@Autowired
	AccountService accountService;

	@Autowired
	AccountConfiguration accountConfiguration;

	@Autowired
	SessionConfiguration sessionConfiguration;

	UsernamePasswordAuthenticationToken authenticationToken;
	Principal principal;

	// Authorized requests
	@PostMapping("/registration")
	public UserProfileDto register(
			@RequestHeader("Authorization") String token) {
		System.out.println("/registration " + token);
		// * add a token to the http session in order to check it on other
		// endpoints *//
		sessionConfiguration.setAttributeToken(token); // CHECK!!!
		return accountService.addUser(token);
	}

	@PostMapping("/profile")
	public UserProfileDto updateUserProfile(
			@RequestBody UserProfileDto userProfileDto, Principal principal) {

		String currentusername = principal.getName();
		System.out.println("currentusername " + currentusername);

		
		return accountService.editUserProfile(userProfileDto, currentusername);
	}

	@GetMapping("/profile")
	public UserProfileDto getUserProfile(Principal principal) {
		System.out.println("@@@@@ '/profile' @@@@@");
		System.out.println("Principal " + principal.getName());

		return accountService.getUserProfile(principal.getName());

	}

	@PostMapping("/login")
	public UserProfileDto loginUser(Principal principal) {
		System.out.println("@@@@@@@@@@@@@@ '/login' @@@@@@@@@@@@@");
		System.out.println("Principal " + principal.getName());
		return accountService.login(principal.getName());

	}

	// Unauthorized requests

	@GetMapping("/staticfields")
	public StaticFieldsDto staticFields(
			@RequestBody StaticFieldsDto staticFieldsDto) {
		return accountService.getStaticFields(staticFieldsDto);

	}
}