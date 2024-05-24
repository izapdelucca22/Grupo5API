package org.serratec.backend.redesocial.config;

import java.util.Arrays;

import org.serratec.backend.redesocial.security.JwtAuthenticationFilter;
import org.serratec.backend.redesocial.security.JwtAuthorizationFilter;
import org.serratec.backend.redesocial.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
		http.csrf(csrf -> csrf.disable()) 
		.cors((cors) -> cors.configurationSource(corsConfigurationSource())) 
		.httpBasic(Customizer.withDefaults()).authorizeHttpRequests(request ->{
			request.requestMatchers(HttpMethod.GET, "/login").permitAll();
			request.requestMatchers("/swagger-ui/**").permitAll();
            request.requestMatchers("/v3/api-docs/**").permitAll();
			request.requestMatchers(HttpMethod.GET, "/**").hasAnyRole("USER", "ADMIN");
			request.requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN");
			request.requestMatchers(HttpMethod.POST, "/**").hasAnyRole("USER", "ADMIN");
			request.requestMatchers(HttpMethod.PUT, "/**").hasAnyRole("USER", "ADMIN");
			request.anyRequest().authenticated();
			//COLOCAR AQUI AS PERMISSÕES PARA CADA ENDPOINT/ROLE |hasAnyAuthority(lista de roles)| hasAuthority(uma role)
		}).sessionManagement(session ->{
			session.sessionCreationPolicy(SessionCreationPolicy.STATELESS); 
		});
		
		JwtAuthenticationFilter jwtAuthenticationfilter = new JwtAuthenticationFilter(
				authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwtUtil);
		jwtAuthenticationfilter.setFilterProcessesUrl("/login");
		
		http.addFilter(jwtAuthenticationfilter); 
		http.addFilter(new JwtAuthorizationFilter(
				authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwtUtil, userDetailsService));
		return http.build();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000/")); 
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")); 
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration.applyPermitDefaultValues());
		return source;
	}
	
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
