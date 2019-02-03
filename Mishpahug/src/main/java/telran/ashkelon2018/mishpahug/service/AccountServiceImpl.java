package telran.ashkelon2018.mishpahug.service;

import java.security.Principal;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Builder;
import telran.ashkelon2018.mishpahug.configuration.AccountConfiguration;
import telran.ashkelon2018.mishpahug.configuration.AccountUserCredentials;
import telran.ashkelon2018.mishpahug.dao.StaticFieldsRepository;
import telran.ashkelon2018.mishpahug.dao.UserAccountRepository;
import telran.ashkelon2018.mishpahug.domain.UserAccount;
import telran.ashkelon2018.mishpahug.dto.StaticFieldsDto;
import telran.ashkelon2018.mishpahug.dto.UserProfileDto;
import telran.ashkelon2018.mishpahug.dto.UserRegDto;
import telran.ashkelon2018.mishpahug.exceptions.UserConflictException;
import telran.ashkelon2018.mishpahug.exceptions.UserNotFoundException;

@Builder
@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	UserAccountRepository userRepository;

	@Autowired
	AccountConfiguration accountConfiguration;
	
	@Autowired
	StaticFieldsRepository staticFieldsRepository;

	@Override
	public UserProfileDto addUser(String token) {
		AccountUserCredentials credentials = accountConfiguration.tokenDecode(token);
		if (userRepository.existsById(credentials.getEmail())) {
			throw new UserConflictException();// create our exception in UserConflictException
		}
		String hashPassword = BCrypt.hashpw(credentials.getPassword(), BCrypt.gensalt());
		// BCrypt.gensalt() method for generate password
		UserAccount userAccount = UserAccount.builder()
				.email(credentials.getEmail())
				.password(hashPassword)
				.build();
		userRepository.save(userAccount);
		return convertToUserProfileDto(userAccount);
	}

	private UserProfileDto convertToUserProfileDto(UserAccount userAccount) {
		return UserProfileDto.builder()
				.firstName(userAccount.getFirstName())
				.lastName(userAccount.getLastName())
//				.dateOfBirth(userAccount.getDateOfBirth())
//				.gender(userAccount.getGender())
//				.maritalStatus(userAccount.getMaritalStatus())
//				.confession(userAccount.getConfession())
//				.pictureLink(userAccount.getPictureLink())
//				.phoneNumber(userAccount.getPhoneNumber())
//				.foodPreferences(userAccount.getFoodPreferences())
//				.languages(userAccount.getLanguages())
//				.description(userAccount.getDescription())
//				.rate(userAccount.getRate())
//				.numberOfVoters(userAccount.getNumberOfVoters())
				.build();
	}

	@Override
	public UserProfileDto editUserProfile(UserProfileDto userProfileDto,String email) {//, String token
		//AccountUserCredentials credentials = accountConfiguration.tokenDecode(token);
		UserAccount userAccount = userRepository.findById(email).get();
		//System.out.println(principal.getName());

		if(userProfileDto.getFirstName()!= null && userProfileDto.getLastName() != null  
//				&& userProfileDto.getPhoneNumber() != null && userProfileDto.getConfession() != null
//        && userProfileDto.getDateOfBirth() != null && userProfileDto.getMaritalStatus() != null && userProfileDto.getFoodPreferences() != null &&userProfileDto.getGender() != null
//        && userProfileDto.getLanguages() != null && userProfileDto.getDescription() != null
        ) {
			userAccount.setFirstName(userProfileDto.getFirstName());
			userAccount.setLastName(userProfileDto.getLastName());
//			userAccount.setPhoneNumber(userProfileDto.getPhoneNumber());
//			userAccount.setConfession(userProfileDto.getConfession());
//			userAccount.setDateOfBirth(userProfileDto.getDateOfBirth());
//			userAccount.setMaritalStatus(userProfileDto.getMaritalStatus());
//			userAccount.setFoodPreferences(userProfileDto.getFoodPreferences());
//			userAccount.setGender(userProfileDto.getGender());

//		if (credentials.getEmail() == userAccount.getEmail()) {
//
//			if (userProfileDto.getFirstName() != null) {
//				userAccount.setFirstName(userProfileDto.getFirstName());
//			}
//			if (userProfileDto.getLastName() != null) {
//				userAccount.setLastName(userProfileDto.getLastName());
//			}
//			if (userProfileDto.getPhoneNumber() != null) {
//				userAccount.setPhoneNumber(userProfileDto.getPhoneNumber());
//			}
//			if (userProfileDto.getConfession() != null) {
//				userAccount.setConfession(userProfileDto.getConfession());
//			}
//			if (userProfileDto.getDateOfBirth() != null) {
//				userAccount.setDateOfBirth(userProfileDto.getDateOfBirth());
//			}
//			if (userProfileDto.getMaritalStatus() != null) {
//				userAccount.setMaritalStatus(userProfileDto.getMaritalStatus());
//			}
//			if (userProfileDto.getFoodPreferences() != null) {
//				userAccount.setFoodPreferences(userProfileDto.getFoodPreferences());
//			}
//			if (userProfileDto.getGender() != null) {
//				userAccount.setGender(userProfileDto.getGender());
//			}
//			if (userProfileDto.getLanguages() != null) {
//				userAccount.setLanguages(userProfileDto.getLanguages());
//			}
//			if (userProfileDto.getDescription() != null) {
//				userAccount.setDescription(userProfileDto.getDescription());
//			}
//			if (userProfileDto.getPictureLink() != null) {
//				userAccount.setPictureLink(userProfileDto.getPictureLink());
//			}
//			if (userProfileDto.getRate() != null) {
//				userAccount.setRate(userProfileDto.getRate());
//			}
//			if (userProfileDto.getNumberOfVoters() != null) {
//				userAccount.setNumberOfVoters(userProfileDto.getNumberOfVoters());
//			}
//			userRepository.save(userAccount);
		} else {
			throw new UserNotFoundException();
		}
		return convertToUserProfileDto(userAccount);
	}

	@Override
	public UserProfileDto login(String token) {
		AccountUserCredentials credentials = accountConfiguration.tokenDecode(token);
		UserAccount userAccount = userRepository.findById(credentials.getEmail()).get();
		return convertToUserProfileDto(userAccount);
	}

	@Override
	public StaticFieldsDto getStaticFields(StaticFieldsDto staticFieldsDto) {
//		StaticFields staticFields = staticFieldsRepository.findById(credentials.getEmail()).get();
		return StaticFieldsDto.builder()
				.confession(staticFieldsDto.getConfession())
				.gender(staticFieldsDto.getGender())
				.maritalStatus(staticFieldsDto.getMaritalStatus())
				.foodPreferences(staticFieldsDto.getFoodPreferences())			
				.languages(staticFieldsDto.getLanguages())
				.holiday(staticFieldsDto.getHoliday())
				.build();
	}

	@Override
	public UserProfileDto getUserProfile(UserProfileDto userProfileDto, String token) {
		// TODO Auto-generated method stub
		return null;
	}

}
