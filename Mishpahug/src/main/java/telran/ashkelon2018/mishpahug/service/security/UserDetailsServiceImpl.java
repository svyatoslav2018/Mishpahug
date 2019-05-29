package telran.ashkelon2018.mishpahug.service.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import telran.ashkelon2018.mishpahug.dao.UserAccountRepository;
import telran.ashkelon2018.mishpahug.domain.UserAccount;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserAccountRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		System.out.println("loadUserByUsername "+ username );
		UserAccount userAccount = repository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		Set<SimpleGrantedAuthority> setRoles = new HashSet<SimpleGrantedAuthority>();
		setRoles.add(new SimpleGrantedAuthority("USER"));
//		System.out.println(
//				"UserDetailsServiceImpl username " + userAccount.getLogin()
//						+ " password " + userAccount.getPassword());
		return new User(userAccount.getLogin(), userAccount.getPassword(),
				setRoles);
	}

}
