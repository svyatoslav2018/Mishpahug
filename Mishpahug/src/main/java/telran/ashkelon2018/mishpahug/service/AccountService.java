
<<<<<<< HEAD
package telran.ashkelon2018.mishpahug.service;


import telran.ashkelon2018.mishpahug.dto.UserProfileDto;

public interface AccountService {
	UserProfileDto addUser(String token);

	UserProfileDto editUser(UserProfileDto userProfileDto, String token);
	
	UserProfileDto login(String token);

//	UserProfileDto getStaticFields(StaticFieldsDto staticFieldsDto);
	
}
=======
package telran.ashkelon2018.mishpahug.service;

import telran.ashkelon2018.mishpahug.dto.StaticFieldsDto;
import telran.ashkelon2018.mishpahug.dto.UserProfileDto;

public interface AccountService {
	UserProfileDto addUser(String token);

	UserProfileDto editUserProfile(UserProfileDto userProfileDto,String email);//, String token

	UserProfileDto getUserProfile(UserProfileDto userProfileDto, String token);

	UserProfileDto login(String token);

	StaticFieldsDto getStaticFields(StaticFieldsDto staticFieldsDto);

}
>>>>>>> branch 'master' of https://github.com/svyatoslav2018/Mishpahug_Backend.git

