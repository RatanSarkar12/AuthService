package com.authSecurity.controllers;

import com.authSecurity.AuthService.AuthService;
import com.authSecurity.Dtos.LoginRequestDto;
import com.authSecurity.Dtos.UserDto;
import com.authSecurity.Exceptions.UserAlreadyExistException;
import com.authSecurity.Exceptions.UserDoesNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Auth")
public class AuthController {

     private AuthService authService;

     public AuthController(AuthService authService){
         this.authService = authService;

     }

     @PostMapping("/singup")
    public ResponseEntity<UserDto>singup(@RequestBody LoginRequestDto request)throws UserAlreadyExistException {
         UserDto userDto = authService.singUp(request.getEmail(),request.getPassword());
         return new ResponseEntity<>(userDto, HttpStatus.OK);
     }
     @PostMapping("/login")
     public ResponseEntity<UserDto>login(@RequestBody LoginRequestDto request) throws UserAlreadyExistException, UserDoesNotFoundException {
         return authService.login(request.getEmail(), request.getPassword());
     }
}
