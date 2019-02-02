package telran.ashkelon2018.mishpahug.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

	@PostMapping("/registration")
	public UserProfileDto register(@RequestHeader("Authorization") String token) {
		return accountService.addUser(token);
	}

	@PostMapping("/profile")
	public UserProfileDto updateUserProfile(
			@RequestHeader("Content-Type: application/json") UserProfileDto userProfileDto,
			@RequestHeader("Authorization") String token) {
		return accountService.editUserProfile(userProfileDto, token);
	}

	@GetMapping("/profile")
	public UserProfileDto getUser(@RequestHeader("Content-Type: application/json") UserProfileDto userProfileDto,
			@RequestHeader("Authorization") String token) {
		return accountService.getUserProfile(userProfileDto, token);
	}

	@PostMapping("/login")
	public UserProfileDto loginUser(@RequestHeader("Content-Type: application/json") UserProfileDto userProfileDto,
			@RequestHeader("Authorization") String token) {
		return accountService.login(token);
	}

	// Unauthorized requests

//	@GetMapping("/staticfields")
//	public UserProfileDto staticFields(
//			@RequestHeader("Content-Type: application/json") StaticFieldsDto staticFieldsDto) {
//		return accountService.getStaticFields(staticFieldsDto);
//
//	}
}
