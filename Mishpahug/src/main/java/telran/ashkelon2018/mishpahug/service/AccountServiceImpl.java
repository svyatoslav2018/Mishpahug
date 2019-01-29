package telran.ashkelon2018.mishpahug.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Builder;
import telran.ashkelon2018.mishpahug.configuration.AccountConfiguration;
import telran.ashkelon2018.mishpahug.configuration.AccountUserCredentials;
import telran.ashkelon2018.mishpahug.dao.UserAccountRepository;
import telran.ashkelon2018.mishpahug.domain.UserAccount;
import telran.ashkelon2018.mishpahug.dto.StaticFieldsDto;
import telran.ashkelon2018.mishpahug.dto.UserProfileDto;
import telran.ashkelon2018.mishpahug.exceptions.UserConflictException;
import telran.ashkelon2018.mishpahug.exceptions.UserNotFoundException;

@Builder
@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	UserAccountRepository userRepository;

	@Autowired
	AccountConfiguration accountConfiguration;

	@Override
	public UserProfileDto addUser(String token) {
		AccountUserCredentials credentials = accountConfiguration.tokenDecode(token);
		if (userRepository.existsById(credentials.getEmail())) {
			throw new UserConflictException();// create our exception in UserConflictException
		}
		String hashPassword = BCrypt.hashpw(credentials.getPassword(), BCrypt.gensalt());// BCrypt.gensalt() method for
																							// generate passw
		UserAccount userAccount = UserAccount.builder().email(credentials.getEmail()).password(hashPassword)
				// .email(userRegDto.getEmail()).password(userRegDto.getPassword())
				.build();
		userRepository.save(userAccount);
		return convertToUserProfileDto(userAccount);
	}

	private UserProfileDto convertToUserProfileDto(UserAccount userAccount) {
		return UserProfileDto.builder()
				.firstName(userAccount.getFirstName())
				.lastName(userAccount.getLastName())
				.phoneNumber(userAccount.getPhoneNumber())
				.userConfession(userAccount.getUserConfession())
				.dateOfBirth(userAccount.getDateOfBirth())
				.maritalStatus(userAccount.getMaritalStatus())
				.foodPreference(userAccount.getFoodPreference())
				.gender(userAccount.getGender())
				.languages(userAccount.getLanguages())
				.aboutYourself(userAccount.getAboutYourself())
				.pictureLink(userAccount.getPictureLink())
				.build();
	}

	@Override
	public UserProfileDto editUser(UserProfileDto userProfileDto, String token) {
		AccountUserCredentials credentials = accountConfiguration.tokenDecode(token);
		UserAccount userAccount = userRepository.findById(credentials.getEmail()).get();
		if (credentials.getEmail() == userAccount.getEmail()) {

			if (userProfileDto.getFirstName() != null) {
				userAccount.setFirstName(userProfileDto.getFirstName());
			}
			if (userProfileDto.getLastName() != null) {
				userAccount.setLastName(userProfileDto.getLastName());
			}
			if (userProfileDto.getPhoneNumber() != null) {
				userAccount.setPhoneNumber(userProfileDto.getPhoneNumber());
			}
			if (userProfileDto.getUserConfession() != null) {
				userAccount.setUserConfession(userProfileDto.getUserConfession());
			}
			if (userProfileDto.getDateOfBirth() != null) {
				userAccount.setDateOfBirth(userProfileDto.getDateOfBirth());
			}
			if (userProfileDto.getMaritalStatus() != null) {
				userAccount.setMaritalStatus(userProfileDto.getMaritalStatus());
			}
			if (userProfileDto.getFoodPreference() != null) {
				userAccount.setFoodPreference(userProfileDto.getFoodPreference());
			}
			if (userProfileDto.getGender() != null) {
				userAccount.setGender(userProfileDto.getGender());
			}
			if (userProfileDto.getLanguages() != null) {
				userAccount.setLanguages(userProfileDto.getLanguages());
			}
			if (userProfileDto.getAboutYourself() != null) {
				userAccount.setAboutYourself(userProfileDto.getAboutYourself());
			}
			if (userProfileDto.getPictureLink() != null) {
				userAccount.setPictureLink(userProfileDto.getPictureLink());
			}
			userRepository.save(userAccount);
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
	public UserProfileDto getStaticFields(StaticFieldsDto staticFieldsDto) {

		return UserProfileDto.builder()
				
				.build();
	}

}
