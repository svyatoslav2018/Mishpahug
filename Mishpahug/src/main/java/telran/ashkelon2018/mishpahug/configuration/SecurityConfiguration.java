package telran.ashkelon2018.mishpahug.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(HttpMethod.POST, "/user/registration");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.httpBasic();
		http.csrf().disable();
		http.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests()
			.antMatchers("/user/**")
			.permitAll();
//		http.authorizeRequests()
//			.antMatchers(ACCOUNT_ROLE+"/**")
//			.hasRole("ADMIN");
//		http.authorizeRequests()
//			.antMatchers(HttpMethod.GET, ACCOUNT, FORUM_POST+"/*")
//			.hasAnyRole("ADMIN", "USER", "MODERATOR");
//		http.authorizeRequests()
//			.antMatchers(HttpMethod.POST, FORUM_POST)
//			.hasAnyRole("ADMIN", "USER", "MODERATOR");
//		http.authorizeRequests()
//			.antMatchers(HttpMethod.PUT, ACCOUNT, FORUM_POST, FORUM_POST+"/*/like",
//					FORUM_POST+"/*/comment")
//			.hasAnyRole("ADMIN", "USER", "MODERATOR");
//		http.authorizeRequests()
//			.antMatchers(HttpMethod.PUT, ACCOUNT_PASSWORD)
//			.authenticated();
//		http.authorizeRequests()
//			.antMatchers(HttpMethod.DELETE, FORUM_POST+"/{id}")
//			.access("@webSecurity.checkAuthorityForDeletePost(authentication,#id)");
//		http.authorizeRequests()
//		.antMatchers("/actuator/**")
//		.hasRole("ADMIN");
		
	
//		String ACCOUNT = "/account"; 
//		String ACCOUNT = "/account/{id}"; 
//		String ACCOUNT_ROLE = "/account/role/{id}/{role}";
//		String ACCOUNT_PASSWORD = "/account/password"; 
//		String FORUM_POST = "/forum/post";
//		String FORUM_POST = "/forum/post/{id}";
//		String FORUM_POST ="/forum/post/{id}/like"; 
//		String FORUM_POST ="/forum/post/{id}/comment";
//		String FORUM_POSTS = "/forum/posts"; 
		
	}
}


