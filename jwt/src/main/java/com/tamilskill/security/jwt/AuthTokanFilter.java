package com.tamilskill.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tamilskill.security.services.UserDetailsServicesImp;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthTokanFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtutils;

	@Autowired
	private UserDetailsServicesImp userDetailsServiceImp;

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		
				if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer")) {
				 return headerAuth.substring(7);	
				}
		return null;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		try {
			String jwt = parseJwt(request);
			if (jwt != null && jwtutils.validateJwtToken(jwt)) {
				String username = jwtutils.getUserNameFromJwtToken(jwt);

				UserDetails userDetails = userDetailsServiceImp.loadUserByUsername(username);

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			}

		} catch (Exception e) {
			logger.error("cannot set user authentication : ()" ,e);
		}
		
		filterChain.doFilter(request, response);
	}
}
