package telran.ashkelon2018.mishpahug.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.forum.service.AccountService;
import telran.ashkelon2018.mishpahug.dto.UserProfileDto;
import telran.ashkelon2018.mishpahug.dto.UserRegDto;

@RestController
@RequestMapping("/account") // all will be start from account
public class AccountManagementController {
	@Autowired
	AccountService accountService;

	@PostMapping//@RequestHeader("Authorization") String token заменить на @RequestBody String token
	public UserProfileDto register(@RequestBody UserRegDto userRegDto, @RequestHeader("Authorization") String token) {
		return accountService.addUser(userRegDto, token);
	}

	@PutMapping
	public UserProfileDto update(@RequestBody UserRegDto userRegDto, @RequestHeader("Authorization") String token) {
		return accountService.editUser(userRegDto, token);
	}

	@GetMapping
	public UserProfileDto loginUser(@RequestHeader("Authorization") String token) {
		return accountService.login(token);
	}
}
