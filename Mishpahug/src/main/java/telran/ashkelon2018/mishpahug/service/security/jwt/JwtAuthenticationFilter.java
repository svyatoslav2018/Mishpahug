package telran.ashkelon2018.mishpahug.service.security.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.server.WebServerException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import telran.ashkelon2018.mishpahug.configuration.AccountUserCredentials;

public class JwtAuthenticationFilter
		extends
			UsernamePasswordAuthenticationFilter {
	 
	private final AuthenticationManager authenticationManager;

	public JwtAuthenticationFilter(
			AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;

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
		// We can use the page https://jwt.io/#debugger to to validate tokenJWT
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
