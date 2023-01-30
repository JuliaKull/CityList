package com.kull.citylist.security;

import com.kull.citylist.dto.UserDto;
import com.kull.citylist.exception.UserException;
import com.kull.citylist.model.User;
import com.kull.citylist.repository.UserRepository;
import com.kull.citylist.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.kull.citylist.service.CityService.MESSAGE;


@Service
@RequiredArgsConstructor
public class AuthenticationService{
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager manager;

    private final UserMapper mapper;

    public AuthenticationResponse register(RegisterRequest request) {
        var userDto = UserDto.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();
        User user = mapper.toEntity(userDto);
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse registerAdmin(RegisterRequest request) {
        var userDto = UserDto.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_ALLOW_EDIT")
                .build();
        User user = mapper.toEntity(userDto);
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = repository.findByUsername(request.getUsername()).orElseThrow(() -> new UserException(MESSAGE));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
