package com.uk.org.ps.publicissapienttask.service;

import com.uk.org.ps.publicissapienttask.model.UserModel;
import com.uk.org.ps.publicissapienttask.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUsername(s);
        if (user != null && user.getUsername().equals(s)) {
            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + s);
        }
    }
}
