package telran.ashkelon2018.mishpahug.service.security.jwt;

public final class SecurityConstants {

    public static final String AUTH_LOGIN_URL = "/user/login";

    // Signing key for HS512 algorithm
    // We can use the page http://www.allkeysgenerator.com/ to generate all kinds of keys
    public static final String JWT_SECRET = "PeShVmYq3t6v9y$B&E)H@McQfTjWnZr4u7x!Г%C*F-JaNdRgUkXp2s5v8y/B?D(G";
   
    // JWT token defaults
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "secure-api";
    public static final String TOKEN_AUDIENCE = "secure-app";
}