package telran.ashkelon2018.mishpahug.service;

import telran.ashkelon2018.mishpahug.dto.UserProfileDto;
import telran.ashkelon2018.mishpahug.dto.UserRegDto;

public interface AccountService {
	UserProfileDto addUser(UserRegDto userRegDto, String token);

	UserProfileDto editUser(UserProfileDto userProfileDto, String token);
	
	UserProfileDto login(String token);
	
}
