
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

