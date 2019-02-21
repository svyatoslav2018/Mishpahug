package telran.ashkelon2018.mishpahug.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Builder;
import telran.ashkelon2018.mishpahug.configuration.AccountConfiguration;
import telran.ashkelon2018.mishpahug.configuration.AccountUserCredentials;
import telran.ashkelon2018.mishpahug.configuration.SessionConfiguration;
import telran.ashkelon2018.mishpahug.dao.StaticFieldsRepository;
import telran.ashkelon2018.mishpahug.dao.UserAccountRepository;
import telran.ashkelon2018.mishpahug.domain.UserAccount;
import telran.ashkelon2018.mishpahug.dto.StaticFieldsDto;
import telran.ashkelon2018.mishpahug.dto.UserProfileDto;
import telran.ashkelon2018.mishpahug.exceptions.UserConflictException;
import telran.ashkelon2018.mishpahug.exceptions.WrongLoginOrPasswordException;

@Builder
@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	UserAccountRepository userRepository;

	@Autowired
	AccountConfiguration accountConfiguration;

	@Autowired
	StaticFieldsRepository staticFieldsRepository;

	@Autowired
	SessionConfiguration sessionConfiguration;

	@Override
	public UserProfileDto addUser(String token) {
		// EmailValidator emailValidator = new EmailValidator();
		// PasswordValidator passwordValidator = new PasswordValidator();
		AccountUserCredentials credentials = accountConfiguration.tokenDecode(token);

		// if (!(emailValidator.validate(credentials.getEmail()))
		// || !(passwordValidator.validate(credentials.getPassword()))) {
		// throw new UnqualifiedLoginOrPassword();// 422 Invalid data. Email or password
		// does not meet the requirements
		// }

		if (userRepository.existsById(credentials.getEmail())) {
			throw new UserConflictException();// 409 User exists
		}

		String hashPassword = BCrypt.hashpw(credentials.getPassword(), BCrypt.gensalt());

		UserAccount userAccount = UserAccount.builder().email(credentials.getEmail()).password(hashPassword).build();

		userRepository.save(userAccount);

		return convertToUserProfileDto(userAccount);
	}

	private UserProfileDto convertToUserProfileDto(UserAccount userAccount) {
		return UserProfileDto.builder().firstName(userAccount.getFirstName()).lastName(userAccount.getLastName())
				.dateOfBirth(userAccount.getDateOfBirth()).gender(userAccount.getGender())
				.maritalStatus(userAccount.getMaritalStatus()).confession(userAccount.getConfession())
				.pictureLink(userAccount.getPictureLink()).phoneNumber(userAccount.getPhoneNumber())
				.foodPreferences(userAccount.getFoodPreferences()).languages(userAccount.getLanguages())
				.description(userAccount.getDescription()).rate(userAccount.getRate())
				.numberOfVoters(userAccount.getNumberOfVoters()).build();
	}

	@Override
	public UserProfileDto editUserProfile(UserProfileDto userProfileDto, String sessionLlogin) {
		// AccountUserCredentials credentials = accountConfiguration.tokenDecode(token);
		// System.out.println("editUserProfile -- " + sessionConfiguration.getEmail());
		UserAccount userAccount = userRepository.findById(sessionLlogin).get();
		System.out.println(sessionLlogin);
		System.out.println(userAccount.getEmail());

		if (!sessionLlogin.equals(userAccount.getEmail())) {
			throw new WrongLoginOrPasswordException();// 401 unauthorized
		}
		userRepository.save(userAccount);
		return convertToUserProfileDto(userAccount);
	}

	@Override
	public UserProfileDto login(String token) {
		AccountUserCredentials credentials = accountConfiguration.tokenDecode(token);
		UserAccount userAccount = userRepository.findById(credentials.getEmail()).get();
		if ((!credentials.getEmail().equals(userAccount.getEmail()))
				|| (!credentials.getPassword().equals(userAccount.getPassword()))) {
			throw new WrongLoginOrPasswordException();// 401 unauthorized
		}
		if (userAccount.getFirstName() == null || userAccount.getLastName() == null
				|| userAccount.getPhoneNumber() == null || userAccount.getConfession() == null
				|| userAccount.getDateOfBirth() == null || userAccount.getMaritalStatus() == null
				|| userAccount.getFoodPreferences() == null || userAccount.getGender() == null
				|| userAccount.getLanguages() == null || userAccount.getDescription() == null) {
			throw new UserConflictException();// 409 empty profile exception
		}
		return convertToUserProfileDto(userAccount);
	}

	@Override
	public UserProfileDto getUserProfile(String token) {
		AccountUserCredentials credentials = accountConfiguration.tokenDecode(token);
		UserAccount userAccount = userRepository.findById(credentials.getEmail()).get();
		if (userAccount.getFirstName() == null || userAccount.getLastName() == null
				|| userAccount.getPhoneNumber() == null || userAccount.getConfession() == null
				|| userAccount.getDateOfBirth() == null || userAccount.getMaritalStatus() == null
				|| userAccount.getFoodPreferences() == null || userAccount.getGender() == null
				|| userAccount.getLanguages() == null || userAccount.getDescription() == null) {
			throw new UserConflictException();// 409 empty profile exception
		}
		return convertToUserProfileDto(userAccount);
	}

	// Unauthorized requests

	@Override
	public StaticFieldsDto getStaticFields(StaticFieldsDto staticFieldsDto) {

		return StaticFieldsDto.builder().confession(staticFieldsDto.getConfession()).gender(staticFieldsDto.getGender())
				.maritalStatus(staticFieldsDto.getMaritalStatus()).foodPreferences(staticFieldsDto.getFoodPreferences())
				.languages(staticFieldsDto.getLanguages()).holliday(staticFieldsDto.getHolliday()).build();
	}

}