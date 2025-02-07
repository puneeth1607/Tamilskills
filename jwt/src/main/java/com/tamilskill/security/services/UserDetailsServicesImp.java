package com.tamilskill.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tamilskill.model.User;
import com.tamilskill.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserDetailsServicesImp implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("user not found with username" + username));
		return UserDetailsImpl.build(user);
	}

}
