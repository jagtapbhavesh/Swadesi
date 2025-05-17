package com.project.swadesi.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.swadesi.entity.Users;
import com.project.swadesi.entity.UsersType;
import com.project.swadesi.repository.UsersRepository;
import com.project.swadesi.repository.UsersTypeRepository;

import java.util.Date;
import java.util.Optional;


@Service
public class UsersService {

	
	@Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersTypeRepository usersTypeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    

    public Users addNew(Users users) {
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        users.setPassword(passwordEncoder.encode(users.getPassword()));

        // Fetch existing UsersType with id = 2 (Customer)
        UsersType customerType = usersTypeRepository.findById(2)
                .orElseThrow(() -> new RuntimeException("Customer user type not found"));

        // Set it to user
        users.setUserTypeId(customerType);

        // Save user
        Users savedUser = usersRepository.save(users);

        return savedUser;
    }

    public Object getCurrentUserProfile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users users = usersRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Could not found " + "user"));
            int userId = users.getUserId();
           
        }

        return null;
    }

    public Optional<Users> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
    
} 
