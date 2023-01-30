package com.kull.citylist.security;

import com.kull.citylist.dto.UserDto;
import com.kull.citylist.model.User;
import com.kull.citylist.repository.UserRepository;
import com.kull.citylist.service.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AuthenticationServiceTest {

    @Mock
    private AuthenticationManager manager;
    @InjectMocks
    private AuthenticationService service;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository repository;
    @Mock
    private JwtService jwtService;

    @Mock
    private UserMapper mapper;

    @Test
    void registerUser() {
        String username = "user";
        String password = "password";
        String encodedPassword = "$2a$10$encoded_password";
        RegisterRequest request = new RegisterRequest(username, password);

        var expectedUser = UserDto.builder()
                .username(username)
                .password(encodedPassword)
                .role("USER")
                .build();

        User user = mapper.toEntity(expectedUser);
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        when(repository.save(user)).thenReturn(user);

        String jwtToken = "token";
        when(jwtService.generateToken(user)).thenReturn(jwtToken);


        AuthenticationResponse response = service.register(request);

        verify(passwordEncoder).encode(password);
        verify(repository).save(user);
        verify(jwtService).generateToken(user);

        assertEquals(jwtToken, response.getToken());
    }


    @Test
    void registerAdmin() {
        String username = "user";
        String password = "password";
        String encodedPassword = "$2a$10$encoded_password";
        RegisterRequest request = new RegisterRequest(username, password);

        var expectedUser = UserDto.builder()
                .username(username)
                .password(encodedPassword)
                .role("ROLE_ALLOW_EDIT")
                .build();

        User user = mapper.toEntity(expectedUser);
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        when(repository.save(user)).thenReturn(user);

        String jwtToken = "token";
        when(jwtService.generateToken(user)).thenReturn(jwtToken);


        AuthenticationResponse response = service.registerAdmin(request);

        verify(passwordEncoder).encode(password);
        verify(repository).save(user);
        verify(jwtService).generateToken(user);

        assertEquals(jwtToken, response.getToken());
    }


    @Test
    void authenticate() {
        String username = "user";
        String password = "password";
        String encodedPassword = "$2a$10$encoded_password";
        AuthenticationRequest request = new AuthenticationRequest(username, password);
        User user = User.builder()
                .username(username)
                .password(encodedPassword)
                .role("USER")
                .build();

        when(manager.authenticate(new UsernamePasswordAuthenticationToken(username, password)))
                .thenReturn(null);

        when(repository.findByUsername(username)).thenReturn(Optional.of(user));

        String jwtToken = "token";

        when(jwtService.generateToken(user)).thenReturn(jwtToken);

        AuthenticationResponse expectedResponse = new AuthenticationResponse(jwtToken);

        AuthenticationResponse actualResponse = service.authenticate(request);

        verify(manager).authenticate(new UsernamePasswordAuthenticationToken(username, password));
        verify(repository).findByUsername(username);
        verify(jwtService).generateToken(user);

        assertEquals(expectedResponse.getToken(), actualResponse.getToken());


    }
}