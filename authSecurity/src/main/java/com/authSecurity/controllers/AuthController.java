package com.authSecurity.controllers;

import com.authSecurity.AuthService.AuthService;
import com.authSecurity.Dtos.LoginRequestDto;
import com.authSecurity.Dtos.UserDto;
import com.authSecurity.Dtos.ValidatetokenResponseDto;
import com.authSecurity.Exceptions.UserAlreadyExistException;
import com.authSecurity.Exceptions.UserDoesNotFoundException;
import com.authSecurity.Model.SessionStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto request) throws UserDoesNotFoundException {
        return authService.login(request.getEmail(), request.getPassword());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody com.scaler.userservicenovttseve.dtos.LogoutRequestDto request) {
        return authService.logout(request.getToken(), request.getUserId());
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody com.scaler.userservicenovttseve.dtos.SignUpRequestDto request) throws UserAlreadyExistException {
        UserDto userDto = authService.signUp(request.getEmail(), request.getPassword());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidatetokenResponseDto> validateToken(@RequestBody com.scaler.userservicenovttseve.dtos.ValidateTokenRequestDto request) {
        Optional<UserDto> userDto = authService.validate(request.getToken(), request.getUserId());

        if (userDto.isEmpty()) {
            ValidatetokenResponseDto response = new ValidatetokenResponseDto();
            response.setSessionStatus(SessionStatus.INVALID);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        ValidatetokenResponseDto response = new ValidatetokenResponseDto();
        response.setSessionStatus(SessionStatus.ACTIVE);
        response.setUserDto(userDto.get());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
