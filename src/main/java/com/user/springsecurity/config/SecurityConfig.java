package com.user.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean   //object (form backbonemof your apln) managed by s IoC container
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();   //pswd encoding and decoding using the bcrypt hashing fn
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.csrf().disable()   //Cross-Site Request production is disabled
				.authorizeHttpRequests((authorize)->
//				authorize.anyRequest().authenticated()
//				).httpBasic(Customizer.withDefaults());  //authendicate any kind of request 
				authorize.requestMatchers("/register/**").permitAll()
                .requestMatchers("/index").permitAll()
                .requestMatchers("/users").hasRole("ADMIN")
).formLogin(
        form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/users")
                .permitAll()
).logout(
        logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll());
		return http.build();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder());
	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//
//		UserDetails users=User.builder()	
//				.username("users")
//				.password(passwordEncoder().encode("12345"))
//				.roles("USER")
//				.build();
//		
//		return new InMemoryUserDetailsManager(users);
//	}
}
