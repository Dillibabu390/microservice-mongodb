package com.ncash.authservice.controller;
import com.ncash.authservice.dto.AuthRequest;
import com.ncash.authservice.entity.UserCredential;
import com.ncash.authservice.response.APIResponseUtil;
import com.ncash.authservice.response.AuthResponse;
import com.ncash.authservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


/**
 * The type Auth controller.
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Add new user response entity.
     *
     * @param user the user
     * @return the response entity
     */
    @PostMapping("/register")
    public ResponseEntity<Object> addNewUser(@Valid @RequestBody UserCredential user) {
        log.trace("UserController application addNewUser method invoked !");
        try {
            Object addUser = service.saveUser(user);
            if (addUser != null)
                return APIResponseUtil.getResponseWithData(addUser);

            return APIResponseUtil.getResponseForEmptyList();
        } catch (Exception e) {
            log.error("Error in UserController Method addNewUser {}",e.getMessage());
            return APIResponseUtil.getResponseWithMessage(e.getMessage());
        }

    }

    /**
     * Gets token.
     *
     * @param authRequest the auth request
     * @return the token
     */
    @PostMapping("/login")
    public Object getToken(@Valid @RequestBody  AuthRequest authRequest) {
        log.info("invoked getToken in AuthController");
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            return AuthResponse.builder().Token(service.generateToken(authRequest.getUsername())).build();
        } else {
            log.error("unexpected error occured in getToken in AuthController");
            throw new RuntimeException("invalid access");
        }
    }


}
