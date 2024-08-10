package com.authSecurity.test;
import com.authSecurity.AuthService.AuthService;
import com.authSecurity.Dtos.UserDto;
import com.authSecurity.Exceptions.UserAlreadyExistException;
import com.authSecurity.security.repository.JpaRegisteredClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.test.annotation.Commit;

import java.util.UUID;

@SpringBootTest
@Commit
public class TestingAuth {

    @Autowired
    private JpaRegisteredClientRepository jpaRegisteredClientRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

    @Autowired
    private AuthService authService;

    @Test
    public void addNewClient() {
        RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("oidc-client")
                //.clientSecret("{noop}secret") // Consider hashing the secret for production
                .clientSecret(bCryptPasswordEncoder.encode("secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/oidc-client")
                .redirectUri("https://oauth.pstmn.io/v1/callback")
                .postLogoutRedirectUri("http://127.0.0.1:8080/")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();

        jpaRegisteredClientRepository.save(oidcClient);
    }
    @Test
    public void addNewUser() throws UserAlreadyExistException {
        String email = "postman@postman.com";
        String password = "postman";
        UserDto userDto = authService.signUp(email,password);
    }
}
