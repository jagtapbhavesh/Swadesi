package com.project.swadesi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.swadesi.entity.Users;
import com.project.swadesi.repository.UsersRepository;
import com.project.swadesi.util.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	 private final UsersRepository usersRepository;

	    @Autowired
	    public CustomUserDetailsService(UsersRepository usersRepository) {
	        this.usersRepository = usersRepository;
	    }

	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        Users user = usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Could not found user"));
	        return new CustomUserDetails(user);
	    }
}
