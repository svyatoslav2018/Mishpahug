package telran.ashkelon2018.mishpahug.service.security.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import telran.ashkelon2018.mishpahug.configuration.AccountUserCredentials;
import telran.ashkelon2018.mishpahug.service.ProducerService;

public class JwtAuthenticationFilter extends	UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

	ProducerService producerService;
	
	public JwtAuthenticationFilter(
			AuthenticationManager authenticationManager, ProducerService producerService) {
		this.authenticationManager = authenticationManager;
		this.producerService = producerService;
		setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {

		String tokenLgn = request.getHeader("Authorization");
		System.out.println("attemptAuthentication token " + tokenLgn);
		String username = jwtTokenDecode(tokenLgn).getLogin();
		String password = jwtTokenDecode(tokenLgn).getPassword();
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				username, password);

		long timestamp = System.currentTimeMillis();
		try {
			producerService.sensorData(username, timestamp);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return authenticationManager.authenticate(authenticationToken);

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain,
			Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		Set<String> roles = user.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toSet());
		System.out.println(
				"successfulAuthentication user " + user + " roles " + roles);
		byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

		String tokenJWT = Jwts.builder()
				.signWith(Keys.hmacShaKeyFor(signingKey),
						SignatureAlgorithm.HS512)
				.setHeaderParam("type", SecurityConstants.TOKEN_TYPE)
				.setIssuer(SecurityConstants.TOKEN_ISSUER)
				.setAudience(SecurityConstants.TOKEN_AUDIENCE)
				.setSubject(user.getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + 864000000))
				.claim("role", roles).compact();
		System.out.println("created tokenJWT " + tokenJWT);
		// We can use the page https://jwt.io/#debugger to validate tokenJWT
		response.addHeader(SecurityConstants.TOKEN_HEADER,
				SecurityConstants.TOKEN_PREFIX + tokenJWT);
	}

	private AccountUserCredentials jwtTokenDecode(String token) {
		int index = token.indexOf(" ");
		token = token.substring(index + 1);
		byte[] base64DecodeBytes = Base64.getDecoder().decode(token);
		token = new String(base64DecodeBytes);
		String[] auth = token.split(":");
		auth[0] = auth[0].toLowerCase().replaceAll("\\.", "");
		return new AccountUserCredentials((auth[0]), auth[1]);
	}
}