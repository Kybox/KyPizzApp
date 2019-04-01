package fr.kybox.kypizzapp.security;

import fr.kybox.kypizzapp.security.jwt.provider.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class Authenticator implements AuthenticationManager {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final JwtProvider jwtProvider;

    @Autowired
    public Authenticator(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        log.info("Authentication = " + authentication.toString());

        return null;
    }
}
