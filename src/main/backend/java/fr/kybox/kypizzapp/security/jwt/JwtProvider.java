package fr.kybox.kypizzapp.security.jwt;

import fr.kybox.kypizzapp.config.property.JwtProperties;
import fr.kybox.kypizzapp.security.jwt.model.JwtUser;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private JwtValidator jwtValidator;

    @Autowired
    private JwtProperties jwtProperties;

    public String generateToken(Authentication authentication, boolean remember) {

        log.info("JwtProvider > generateToken");
        return jwtGenerator.generate(authentication, remember);
    }

    public Authentication getAuthentication(String token) {

        log.info("JwtProvider > getAuthentication");

        JwtUser jwtUser = jwtValidator.validate(token);

        return new UsernamePasswordAuthenticationToken(jwtUser, token, jwtUser.getAuthorities());
    }

    public boolean validateToken(String token) {

        log.info("JwtProvider > validateToken");

        try {

            Jwts.parser()
                    .setSigningKey(jwtProperties.getSigningKey())
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {

            log.warn("Validate token :");
            log.warn(e.getMessage());
        }

        return false;
    }
}
