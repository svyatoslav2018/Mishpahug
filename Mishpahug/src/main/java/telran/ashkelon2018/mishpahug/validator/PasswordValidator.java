package telran.ashkelon2018.mishpahug.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
	private Pattern pattern;
	private Matcher matcher;
	private static final String PASSWORD_PATTERN = "^{6,}$";
//(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}
	public PasswordValidator() {
		pattern = Pattern.compile(PASSWORD_PATTERN);
	}

	public boolean validate(final String hex) {
		matcher = pattern.matcher(hex);

		return matcher.matches();
	}

}