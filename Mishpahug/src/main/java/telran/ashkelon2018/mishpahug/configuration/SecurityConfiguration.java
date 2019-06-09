package telran.ashkelon2018.mishpahug.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import telran.ashkelon2018.mishpahug.service.security.jwt.JwtAuthenticationFilter;
import telran.ashkelon2018.mishpahug.service.security.jwt.JwtAuthorizationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@CrossOrigin
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	public CustomAuthenticationEntryPoint authenticationEntryPoint;

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(HttpMethod.POST, "/user/registration");
//		web.ignoring().antMatchers(HttpMethod.POST, "/user/profile");
//		web.ignoring().antMatchers(HttpMethod.GET, "/event/subscribed/{eventId}");
//		web.ignoring().antMatchers(HttpMethod.PUT, "/event/subscription/{eventId}");
//		web.ignoring().antMatchers(HttpMethod.GET, "/event/participationlist");
//		web.ignoring().antMatchers(HttpMethod.PUT, "/event/invitation/{eventId}/{subscriberId}");
//		web.ignoring().antMatchers(HttpMethod.PUT, "/event/vote/{eventId}/{voteCount}");
//		web.ignoring().antMatchers(HttpMethod.PUT, "/event/pending/{eventId}");

	}

	@Override
	protected void configure(HttpSecurity httpSec) throws Exception {
		httpSec.csrf().disable();
		httpSec.cors();

		httpSec.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		httpSec.authorizeRequests().antMatchers(HttpMethod.POST, "/event/allprogresslist").permitAll();
		
		httpSec.authorizeRequests().anyRequest().authenticated().and()
				.addFilter(new JwtAuthenticationFilter(authenticationManager()))
				.addFilter(new JwtAuthorizationFilter(authenticationManager()));
	}

	//CORS config
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("GET, POST, PUT, DELETE")
						.allowedOrigins("http://localhost:3000").allowCredentials(true)
						.allowedHeaders("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization")
						.exposedHeaders("Access-Control-Allow-Headers",
								"Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, "
										+ "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers")
						.maxAge(1800).allowCredentials(true);
			}
		};
	}

}