package com.project.swadesi.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.project.swadesi.controller.CollectionController;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger log = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);
    
    @Autowired
    private HttpSession session;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        System.out.println("The username " + username + " is logged in.");
        session.setAttribute("USER_ID", userDetails.getUsername());

        boolean hasJobSeekerRole = authentication.getAuthorities().stream().anyMatch(r->r.getAuthority().equalsIgnoreCase("admin"));
        boolean hasRecruiterRole = authentication.getAuthorities().stream().anyMatch(r->r.getAuthority().equalsIgnoreCase("customer"));

        log.info("Admin ::" + hasJobSeekerRole);
        log.info("Customer :: "+hasRecruiterRole);
        log.info("then what ::"+authentication.getAuthorities().stream().anyMatch(r->r.getAuthority().isEmpty()));
        log.info("then what ::"+authentication.getAuthorities().toString());

        if (hasRecruiterRole || hasJobSeekerRole) {
            response.sendRedirect("/");
        }
    }
}
