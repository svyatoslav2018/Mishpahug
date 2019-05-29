package telran.ashkelon2018.mishpahug.service.security.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.server.WebServerException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import telran.ashkelon2018.mishpahug.domain.UserAccount;
import telran.ashkelon2018.mishpahug.service.security.UserDetailsServiceImpl;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	UserDetailsServiceImpl detailsServiceImpl;
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		System.out.println("HttpServletRequest " + request);
		UsernamePasswordAuthenticationToken authentication = getAuthentication(
				request);
		String header = request.getHeader(SecurityConstants.TOKEN_HEADER);

		if (StringUtils.isEmpty(header)
				|| !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

	@SuppressWarnings("unchecked")
	private UsernamePasswordAuthenticationToken getAuthentication(
			HttpServletRequest request) {

		String jwtToken = request.getHeader(SecurityConstants.TOKEN_HEADER);
		if (!StringUtils.isEmpty(jwtToken)) {
			try {
				System.out.println("JWT Body jwtToken : " + jwtToken);

				Jws<Claims> parsedToken = Jwts.parser()
						.setSigningKey(SecurityConstants.JWT_SECRET.getBytes())
						.parseClaimsJws(jwtToken.replace("Bearer ", ""));
				String username = parsedToken.getBody().getSubject();
				List<SimpleGrantedAuthority> authorities = ((List<String>) parsedToken
						.getBody()
						.get("role"))
						.stream()
								.map(authority -> new SimpleGrantedAuthority((String) authority))
								.collect(Collectors.toList());
				if (!StringUtils.isEmpty(username)) {
					UsernamePasswordAuthenticationToken userNPAT = new UsernamePasswordAuthenticationToken(
							username, null, authorities);
					System.out.println(
							"UsernamePasswordAuthenticationToken " + userNPAT);
					return userNPAT;
				}
			} catch (SignatureException e) {
				throw new WebServerException(
						SecurityConstants.JWT_SECRET + " is expired", e);
			} catch (ExpiredJwtException e) {
				throw new WebServerException(
						SecurityConstants.JWT_SECRET + " is expired", e);
			} catch (UnsupportedJwtException e) {
				throw new WebServerException(jwtToken
						+ " receiving particular format JWT doesn't match the format expected by the application",
						e);
			} catch (MalformedJwtException e) {
				throw new WebServerException(jwtToken + " is not correct", e);
			}
		}
		return null;
	}
}