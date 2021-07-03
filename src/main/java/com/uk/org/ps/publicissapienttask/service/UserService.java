package com.uk.org.ps.publicissapienttask.service;

import com.uk.org.ps.publicissapienttask.config.PasswordEncoderByCrypt;
import com.uk.org.ps.publicissapienttask.dto.UserDTO;
import com.uk.org.ps.publicissapienttask.exception.UserNameAlreadyExistsException;
import com.uk.org.ps.publicissapienttask.model.UserModel;
import com.uk.org.ps.publicissapienttask.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserModel addDetails(UserDTO userDto) {
        UserModel user = userRepository.findByUsername(userDto.getUsername());
        if (user != null) {
            throw new UserNameAlreadyExistsException("User with username:" + userDto.getUsername() + " already exists.");
        }
        userDto.setPassword(passwordEncoder
                .encode(userDto.getPassword()));

        UserModel userModel = new UserModel();
        userModel.setUsername(userDto.getUsername());
        userModel.setPassword(userDto.getPassword());
        userModel.setFirstName(userDto.getFirstName());
        userModel.setLastName(userDto.getLastName());
        return userRepository.save(userModel);
    }

}

