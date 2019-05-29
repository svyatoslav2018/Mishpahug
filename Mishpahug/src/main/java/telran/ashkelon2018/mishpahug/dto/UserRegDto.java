package telran.ashkelon2018.mishpahug.dto;

import org.springframework.data.annotation.Id;

import lombok.Getter;

@Getter
public class UserRegDto {
	@Id
	String login;// email as login. Email without point in DB
	String password;
	
}
