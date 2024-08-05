package com.authSecurity.Dtos;

import com.authSecurity.Dtos.UserDto;
import com.authSecurity.Model.SessionStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidatetokenResponseDto {
    private UserDto userDto;
    private SessionStatus sessionStatus;
}
