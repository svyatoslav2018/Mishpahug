package telran.ashkelon2018.mishpahug.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import telran.ashkelon2018.mishpahug.dao.UserAccountRepository;
import telran.ashkelon2018.mishpahug.domain.UserAccount;

public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	UserAccountRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		UserAccount userAccount = 
				repository.findById(username).
				orElseThrow(() -> new UsernameNotFoundException(username));
		String password = userAccount.getPassword();
		String setRoles = userAccount.getStandartrole();
	
		return new User(username, password, 
				AuthorityUtils.createAuthorityList(setRoles));
	}

}

