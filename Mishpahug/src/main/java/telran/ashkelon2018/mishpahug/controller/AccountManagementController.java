package telran.ashkelon2018.mishpahug.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.GetMapping;
>>>>>>> branch 'master' of https://github.com/svyatoslav2018/Mishpahug_Backend.git
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.mishpahug.dto.UserProfileDto;
import telran.ashkelon2018.mishpahug.service.AccountService;

@RestController
<<<<<<< HEAD
@RequestMapping("/user") // all will be start from account
@CrossOrigin // CORS !!!!!!!!!!!!!!!!! no work
=======
@RequestMapping("/user") // all will be start from user
>>>>>>> branch 'master' of https://github.com/svyatoslav2018/Mishpahug_Backend.git
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
<<<<<<< HEAD
	
	//Unauthorized requests
	
//	@GetMapping("/staticfields")
//	public UserProfileDto staticFields(@RequestBody StaticFieldsDto staticFieldsDto) {
//		return accountService.getStaticFields(staticFieldsDto);
//	}
=======

	// Unauthorized requests

	@GetMapping("/staticfields")
	public StaticFieldsDto staticFields(@RequestBody StaticFieldsDto staticFieldsDto) {
		return accountService.getStaticFields(staticFieldsDto);

	}
>>>>>>> branch 'master' of https://github.com/svyatoslav2018/Mishpahug_Backend.git
}
