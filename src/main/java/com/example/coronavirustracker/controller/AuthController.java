package com.example.coronavirustracker.controller;

import com.example.coronavirustracker.dto.AuthenticationResponse;
import com.example.coronavirustracker.dto.LoginRequestDto;
import com.example.coronavirustracker.dto.RegisterUserRequestDto;
import com.example.coronavirustracker.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * This end point is used for registering new user
     * AUTHORITY=ACCESSIBLE_TO_ALL
     */
    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<String> signup(@RequestBody RegisterUserRequestDto registerUserRequestDto){
        authService.signup(registerUserRequestDto);
        return new ResponseEntity<>("User Registration Successful",HttpStatus.OK);
    }

    /**
     * This end point is used for registering new user
     * AUTHORITY=ACCESSIBLE_TO_ALL
     */
    @PostMapping("/signin")
    public AuthenticationResponse login(@RequestBody LoginRequestDto loginRequestDto){
        return authService.login(loginRequestDto);

    }


}
