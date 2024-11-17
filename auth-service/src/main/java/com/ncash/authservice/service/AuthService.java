package com.ncash.authservice.service;


import com.ncash.authservice.constant.ResponseMessage;
import com.ncash.authservice.entity.UserCredential;
import com.ncash.authservice.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * The type Auth service.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserCredentialRepository repository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    /**
     * Save user object.
     *
     * @param credential the credential
     * @return the object
     */
    public Object saveUser(UserCredential credential) {
        if (!repository.findByName(credential.getName()).isEmpty()) {
            return ResponseMessage.USER_ALREADY_PRESENT;
        }
        credential.setId(UUID.randomUUID().toString().split("-")[0]);
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        return ResponseMessage.USER_CREATED_SUCESSFULLY;
    }

    /**
     * Generate token string.
     *
     * @param username the username
     * @return the string
     */
    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }


}
