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
import telran.ashkelon2018.mishpahug.exceptions.UserNotFoundException;
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

		// if (!(emailValidator.validate(credentials.getLogin()))
		// || !(passwordValidator.validate(credentials.getPassword()))) {
		// throw new UnprocessableEntity(422, "Invalid data. Email or password");
		// does not meet the requirements
		// }

		if (userRepository.existsById(credentials.getLogin())) {
			throw new UserConflictException(409, "User exists");
		}

		String hashPassword = BCrypt.hashpw(credentials.getPassword(), BCrypt.gensalt());
//		String loginLowerCase=credentials.getLogin().toLowerCase();
//		String loginLowerCaseNoDot=loginLowerCase.replaceAll("\\.", "");
		UserAccount userAccount = UserAccount.builder().login(credentials.getLogin()).password(hashPassword).build();
		userRepository.save(userAccount);
		return convertToUserProfileDto(userAccount);
	}

	private UserProfileDto convertToUserProfileDto(UserAccount userAccount) {
		return UserProfileDto.builder()
				.firstName(userAccount.getFirstName())
				.lastName(userAccount.getLastName())
				.dateOfBirth(userAccount.getDateOfBirth())
				.gender(userAccount.getGender())
				.maritalStatus(userAccount.getMaritalStatus())
				.confession(userAccount.getConfession())
				.pictureLink(userAccount.getPictureLink())
				.phoneNumber(userAccount.getPhoneNumber())
				.foodPreferences(userAccount.getFoodPreferences())
				.languages(userAccount.getLanguages())
				.description(userAccount.getDescription())
				.rate(userAccount.getRate())
				.numberOfVoters(userAccount.getNumberOfVoters())
				.build();
	}

	@Override
	public UserProfileDto editUserProfile(UserProfileDto userProfileDto, String sessionLogin) {

		UserAccount userAccount = userRepository.findById(sessionLogin).get();

		if (!sessionLogin.equals(userAccount.getLogin())) {
			throw new WrongLoginOrPasswordException(401, "unauthorized");
		}

		userAccount.setFirstName(userProfileDto.getFirstName());
		userAccount.setLastName(userProfileDto.getLastName());
		userAccount.setPhoneNumber(userProfileDto.getPhoneNumber());
		userAccount.setConfession(userProfileDto.getConfession());
		userAccount.setDateOfBirth(userProfileDto.getDateOfBirth());
		userAccount.setMaritalStatus(userProfileDto.getMaritalStatus());
		userAccount.setFoodPreferences(userProfileDto.getFoodPreferences());
		userAccount.setGender(userProfileDto.getGender());
		userAccount.setLanguages(userProfileDto.getLanguages());
		userAccount.setDescription(userProfileDto.getDescription());
		userAccount.setPictureLink(userProfileDto.getPictureLink());

		userRepository.save(userAccount);
		return convertToUserProfileDto(userAccount);
	}

	@Override
	public UserProfileDto login(String token) {
		AccountUserCredentials credentials = accountConfiguration.tokenDecode(token);
		UserAccount userAccount = userRepository.findById(credentials.getLogin()).orElseThrow(UserNotFoundException::new);//.get()
		String candidatPassword = credentials.getPassword();
		
		if (!credentials.getLogin().equals(userAccount.getLogin())
				|| !BCrypt.checkpw(candidatPassword, userAccount.getPassword())) {
			throw new WrongLoginOrPasswordException(401, "unauthorized");		}

		if (userAccount.getFirstName() == null || userAccount.getLastName() == null
				|| userAccount.getPhoneNumber() == null || userAccount.getConfession() == null
				|| userAccount.getDateOfBirth() == null || userAccount.getMaritalStatus() == null
				|| userAccount.getFoodPreferences() == null || userAccount.getGender() == null
				|| userAccount.getLanguages() == null || userAccount.getDescription() == null) {
			throw new UserConflictException(409, "empty profile exception");
		}
		return convertToUserProfileDto(userAccount);
	}

	@Override
	public UserProfileDto getUserProfile(String sessionLogin) {
		UserAccount userAccount = userRepository.findById(sessionLogin).get();

		if (userAccount.getFirstName() == null || userAccount.getLastName() == null
				|| userAccount.getPhoneNumber() == null || userAccount.getConfession() == null
				|| userAccount.getDateOfBirth() == null || userAccount.getMaritalStatus() == null
				|| userAccount.getFoodPreferences() == null || userAccount.getGender() == null
				|| userAccount.getLanguages() == null || userAccount.getDescription() == null) {
			throw new UserConflictException(409, "empty profile exception");
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