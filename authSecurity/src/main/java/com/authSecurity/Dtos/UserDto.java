package com.authSecurity.Dtos;

import com.authSecurity.Model.Role;
import com.authSecurity.Model.User;
import com.authSecurity.repositories.UserRepository;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDto {
    private String email;
    private Set<Role> roles = new HashSet<>();

    public static UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(userDto.getEmail());
        userDto.setRoles(userDto.getRoles());
        return userDto;

    }
}