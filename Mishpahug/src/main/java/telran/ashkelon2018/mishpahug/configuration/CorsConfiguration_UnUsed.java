package telran.ashkelon2018.mishpahug.configuration;
//package telran.ashkelon2018.mishpahug.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//// перенес все в SecurityConfiguration
//@Configuration
//@EnableWebMvc
//public class CorsConfiguration implements WebMvcConfigurer {
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**")
//						.allowedMethods("GET, HEAD, PUT, POST, DELETE")
//						.allowedOrigins("http://localhost:3000")
//						.allowCredentials(true);
//
//			}
//		};
//	}
//}
//
