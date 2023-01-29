package com.kull.citylist.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public record AuthenticationController(AuthenticationService service) {

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok().body(service.register(request));
    }

    @PostMapping("/register/admin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(@RequestBody RegisterRequest request){
        return ResponseEntity.ok().body(service.registerAdmin(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok().body(service.authenticate(request));
    }


}
