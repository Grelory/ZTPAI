package com.grelory.quickbill.services;

import com.grelory.quickbill.entity.User;
import com.grelory.quickbill.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthService(
            @Autowired UsersRepository usersRepository,
            @Autowired JwtService jwtService,
            @Autowired AuthenticationManager authenticationManager,
            @Autowired PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findUserByToken(String token) {
        return usersRepository.findByUserEmail(jwtService.extractEmail(token));
    }

    public void saveUser(String email, String password, String userName) {
        User newUser = new User();
        newUser.setUserEmail(email);
        newUser.setUserPassword(passwordEncoder.encode(password));
        newUser.setUserName(userName);
        newUser.setRole("USER");
        usersRepository.save(newUser);
    }

    public Optional<String> verifyAndGenerateJwt(String email, String password) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        if (authenticationManager.authenticate(authentication).isAuthenticated()) {
            return Optional.ofNullable(jwtService.generateToken(email));
        }
        return Optional.empty();
    }
}
