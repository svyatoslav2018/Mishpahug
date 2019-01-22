package telran.ashkelon2018.mishpahug.service;

import java.util.Set;

public interface AccountService {
	
    UserProfileDto addUser(UserRegDto userRegDto, String token);
	
	UserProfileDto editUser(UserRegDto userRegDto, String login);
	
	UserProfileDto getUser(String login);
	

}
