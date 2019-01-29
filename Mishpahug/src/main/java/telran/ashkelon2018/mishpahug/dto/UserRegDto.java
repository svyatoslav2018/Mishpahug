package telran.ashkelon2018.mishpahug.dto;

import org.springframework.data.annotation.Id;

import lombok.Getter;

@Getter
public class UserRegDto {
	@Id
	String email;//login
	String password;
}
