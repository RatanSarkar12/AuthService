package com.authSecurity.AuthService;

import com.authSecurity.Dtos.UserDto;
import com.authSecurity.Exceptions.UserAlreadyExistException;
import com.authSecurity.Exceptions.UserDoesNotFoundException;
import com.authSecurity.Model.Session;
import com.authSecurity.Model.SessionStatus;
import com.authSecurity.Model.User;
//import com.authSecurity.repositories.SessionRepository;
import com.authSecurity.repositories.SessionRepository;
import com.authSecurity.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import java.util.HashMap;
import java.util.Optional;
@Service
public class AuthService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserRepository userRepository;

    private SessionRepository sessionRepository;

    public AuthService(UserRepository userRepository,PasswordEncoder passwordEncoder,SessionRepository sessionRepository){

         this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
         this.userRepository = userRepository;
         this.sessionRepository=sessionRepository;

    }
    public ResponseEntity<UserDto>login(String email,String password)throws UserDoesNotFoundException {
         Optional<User>userOptional=userRepository.findByEmail(email);

         if(userOptional.isEmpty()){
             throw new UserDoesNotFoundException("user with "+email+" doesnt exist");
         }
         User user =userOptional.get();
         if(!bCryptPasswordEncoder.matches(password,user.getPassword())){
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String token = RandomStringUtils.randomAscii(20);
        MultiValueMapAdapter<String,String>headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add("AUTH_TOKEN",token);

        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        session.setUser(user);
        sessionRepository.save(session);

        UserDto userDto= UserDto.from(user);
        ResponseEntity<UserDto>response = new ResponseEntity<>(
                userDto,
                headers,HttpStatus.OK
        );
        return response;
    }
    public UserDto singUp(String email,String password) throws UserAlreadyExistException {
        Optional<User>userOptional = userRepository.findByEmail(email);

        if(!userOptional.isEmpty()){

            throw new UserAlreadyExistException("user with "+email+" already exist");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        User saveUser = userRepository.save(user);

        return UserDto.from(saveUser);

    }
}
