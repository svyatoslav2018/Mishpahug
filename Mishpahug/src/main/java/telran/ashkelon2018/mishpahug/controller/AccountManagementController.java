package telran.ashkelon2018.mishpahug.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.mishpahug.dto.StaticFieldsDto;
import telran.ashkelon2018.mishpahug.dto.UserProfileDto;
import telran.ashkelon2018.mishpahug.service.AccountService;

@RestController
@RequestMapping("/user") // all will be start from user
public class AccountManagementController {
	@Autowired
	AccountService accountService;

	// Authorized requests
	@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
	@PostMapping("/registration")
	public UserProfileDto register(@RequestHeader("Authorization") String token) {
		return accountService.addUser(token);
	}

	@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
	@PostMapping("/profile")
	public UserProfileDto updateUserProfile(@RequestBody UserProfileDto userProfileDto,Principal principal) {
		return accountService.editUserProfile(userProfileDto,principal.getName());
	}

	@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
	@GetMapping("/profile")
	public UserProfileDto getUser(@RequestBody UserProfileDto userProfileDto,
			@RequestHeader("Authorization") String token) {
		return accountService.getUserProfile(userProfileDto, token);
	}

	@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
	@PostMapping("/login")
	public UserProfileDto loginUser(@RequestHeader("Authorization") String token) {
		return accountService.login(token);
	}

	// Unauthorized requests

	@GetMapping("/staticfields")
	public StaticFieldsDto staticFields(@RequestBody StaticFieldsDto staticFieldsDto) {
		return accountService.getStaticFields(staticFieldsDto);

	}
}
