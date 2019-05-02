package telran.ashkelon2018.mishpahug.service.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import telran.ashkelon2018.mishpahug.domain.UserAccount;

public class UserDetailsImpl implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	UserAccount userAccount;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		return userAccount.getPassword();
	}

	@Override
	public String getUsername() {
		return userAccount.getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
