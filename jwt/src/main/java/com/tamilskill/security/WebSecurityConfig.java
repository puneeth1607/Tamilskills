package com.tamilskill.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tamilskill.security.jwt.AuthEntryPointJwt;
import com.tamilskill.security.jwt.AuthTokanFilter;
import com.tamilskill.security.services.UserDetailsServicesImp;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

	@Autowired
	private UserDetailsServicesImp UserDetailsService;	
	
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokanFilter authenticationJwtTokenFilter() {
		return new AuthTokanFilter();
	}

	@Bean
	public DaoAuthenticationProvider authonticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

		authenticationProvider.setUserDetailsService(UserDetailsService);

		authenticationProvider.setPasswordEncoder(passwordEncoder());

		return authenticationProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterchain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/**","/api/auth/signin").permitAll()
					.requestMatchers(HttpMethod.POST, "/api/auth/**", "/api/auth/signin/**","/api/auth/signup/**").permitAll()
						.requestMatchers("api/test/**", "/api/auth/signin**").permitAll().anyRequest().authenticated());

		http.authenticationProvider(authonticationProvider());

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
//
//    @SuppressWarnings("deprecation")
//	@Bean
//	
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())
//                .exceptionHandling(handling -> handling.authenticationEntryPoint(unauthorizedHandler))
//                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/**", "/api/auth/signin").permitAll()
//                        .requestMatchers("/api/test/**", "/api/auth/signin").permitAll()
//                        .anyRequest().authenticated());
//
//        http.authenticationProvider(authonticationProvider());
//        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
	
	
//	@Bean
//	protected void configure(HttpSecurity http) throws Exception {
//		http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
//				.antMatchers(HttpMethod.GET,"/jobseekerapp/v1/forgotPassword/*").permitAll()
//				.antMatchers(HttpMethod.POST,"/jobseekerapp/v1/register","/jobseekerapp/v1/userLogin","/file/uploadFile", "/file/uploadFile/","/jobseekerapp/v1/generateOtp","/jobseekerapp/v1/resetPassword").permitAll().anyRequest()
//				.authenticated();
//		http.headers().xssProtection().block(Boolean.FALSE).disable();
//		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//	}

}
