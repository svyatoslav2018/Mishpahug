
	package telran.ashkelon2018.mishpahug.service;

	import telran.ashkelon2018.mishpahug.dto.StaticFieldsDto;
	import telran.ashkelon2018.mishpahug.dto.UserProfileDto;

	public interface AccountService {
		UserProfileDto addUser(String token);

		UserProfileDto editUserProfile(UserProfileDto userProfileDto,String email);//, String token

		UserProfileDto getUserProfile(String token);

		UserProfileDto login(String login);

		StaticFieldsDto getStaticFields(StaticFieldsDto staticFieldsDto);
		

	}

