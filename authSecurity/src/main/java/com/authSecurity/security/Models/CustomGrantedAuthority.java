package com.authSecurity.security.Models;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Role;
import org.springframework.security.core.GrantedAuthority;


@JsonDeserialize
@NoArgsConstructor
public class CustomGrantedAuthority implements GrantedAuthority {
    //    private Role role;
    private String authority;

    public CustomGrantedAuthority(Role role) {
        this.authority = role.annotationType().getName();
    }


    @Override
    public String getAuthority() {
        return this.authority;
    }
}