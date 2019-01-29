package telran.ashkelon2018.mishpahug.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.mishpahug.dto.UserProfileDto;
import telran.ashkelon2018.mishpahug.service.AccountService;

@RestController
@RequestMapping("/account") // all will be start from account
public class AccountManagementController {
	@Autowired
	AccountService accountService;

	@PostMapping("/register")//@RequestHeader("Authorization") String token заменить на @RequestBody String token
	public UserProfileDto register( @RequestHeader("Authorization") String token) {
		return accountService.addUser(token);
	}

	@PutMapping
	public UserProfileDto update(@RequestBody UserProfileDto userProfileDto, @RequestBody String token) {
		return accountService.editUser(userProfileDto, token);
	}
	
	@GetMapping
	public UserProfileDto loginUser(@RequestHeader("Authorization") String token) {
		return accountService.login(token);
	}
}
