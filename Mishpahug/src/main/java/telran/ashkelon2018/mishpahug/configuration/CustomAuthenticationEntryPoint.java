package telran.ashkelon2018.mishpahug.configuration;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
@Configuration
public class CustomAuthenticationEntryPoint extends BasicAuthenticationEntryPoint{
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
        throws IOException, ServletException {
    	
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        System.out.println("HTTP Status 401 - " + authException.getMessage());
        writer.println("HTTP Status 401 - " + authException.getMessage());
    	 System.out.println(request.getMethod());
    	System.out.println(request.getHeader(HttpHeaders.ORIGIN));
    	 System.out.println(response);
    	System.out.println("1 " + response.encodeURL(getRealmName()));
//       response.setHeader("WWW-Authenticate", "Basic");
//       response.addHeader("WWW-Authenticate", "FormBasic realm=" + getRealmName());
 //      response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
 //      response.setHeader("Access-Control-Allow-Credentials", "true");
        System.out.println("2 " + response.getHeaderNames());

    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("mishpahug");
        super.afterPropertiesSet();
    }
}
