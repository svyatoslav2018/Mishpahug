package telran.ashkelon2018.mishpahug.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.mishpahug.dto.UserProfileDto;
import telran.ashkelon2018.mishpahug.dto.UserRegDto;
import telran.ashkelon2018.mishpahug.service.AccountService;

@RestController
@RequestMapping("/account") // all will be start from account
public class AccountManagementController {
	@Autowired
	AccountService accountService;

	@PostMapping//@RequestHeader("Authorization") String token заменить на @RequestBody String token
	public UserProfileDto register(@RequestBody UserRegDto userRegDto, @RequestBody String token) {
		return accountService.addUser(userRegDto, token);
	}

	@PutMapping
	public UserProfileDto update(@RequestBody UserRegDto userRegDto, @RequestBody String token) {
		return accountService.editUser(userRegDto, token);
	}

	@GetMapping
	public UserProfileDto loginUser(@RequestBody String token) {
		return accountService.login(token);
	}
}
