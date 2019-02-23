package telran.ashkelon2018.mishpahug.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
	private Pattern pattern;
	private Matcher matcher;

	private static final String EMAIL_PATTERN = "^[a-zA-Z0-9]{1,}" + "((\\.|\\_|-{0,1})[a-zA-Z0-9]{1,})*" + "@"
			+ "[a-zA-Z0-9]{1,}" + "((\\.|\\_|-{0,1})[a-zA-Z0-9]{1,})*" + "\\.[a-zA-Z]{2,}$";

	/*
	 * Что проверяет код: должна быть латиница, первым должен быть символ латиницы
	 * от а до z в любом регистре или цифра; далее может встречаться или “.” или “_”
	 * или “-“, но обязательно с последующим одним или более символом латиницы или
	 * цифрой(рами) и все это много раз(это исключает задвоение перечисленных
	 * символов типа “..”, “—“, “_____”); далее один символ @; далее хоть один
	 * символ латиницы или цифра(или множество оных); снова допустимые знаки без
	 * задвоения; и концовка: обязательная точка, и два и более символа латиницей.
	 */
	public EmailValidator() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	public boolean validate(final String hex) {
		matcher = pattern.matcher(hex);

		return matcher.matches();
	}

}
// ^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
// + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"