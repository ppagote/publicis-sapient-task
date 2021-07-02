package com.uk.org.ps.publicissapienttask.service;

import com.uk.org.ps.publicissapienttask.config.PasswordEncoder;
import com.uk.org.ps.publicissapienttask.dto.UserDTO;
import com.uk.org.ps.publicissapienttask.exception.UserNameAlreadyExistsException;
import com.uk.org.ps.publicissapienttask.model.UserModel;
import com.uk.org.ps.publicissapienttask.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserModel addDetails(UserDTO userDto) {
        UserModel user = userRepository.findByUsername(userDto.getUsername());
        if (user != null) {
            throw new UserNameAlreadyExistsException("User with username:" + userDto.getUsername() + " already exists.");
        }
        userDto.setPassword(passwordEncoder.bCryptPasswordEncoder()
                .encode(userDto.getPassword()));

        UserModel userModel = new UserModel();
        userModel.setUsername(userDto.getUsername());
        userModel.setPassword(userDto.getPassword());
        userModel.setFirstName(userDto.getFirstName());
        userModel.setLastName(userDto.getLastName());
        return userRepository.save(userModel);
    }

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

