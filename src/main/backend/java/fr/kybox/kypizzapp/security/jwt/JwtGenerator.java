package fr.kybox.kypizzapp.security.jwt;

import fr.kybox.kypizzapp.config.property.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

import static fr.kybox.kypizzapp.utils.constant.ValueObject.*;

@Component
public class JwtGenerator {

    private final JwtProperties jwtProperties;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public JwtGenerator(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    String generate(Authentication authentication, boolean remember) {

        log.info("JwtGenerator > generate");

        String authorityList = authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(COMMA));

        Claims claims = Jwts.claims().setSubject(authentication.getPrincipal().toString());
        //claims.put(JWT_ID, authentication.getId());
        claims.put(JWT_AUTHORITIES, authorityList);

        Date exp = Date.from(Instant.now().plusSeconds(jwtProperties.getExpiration()));
        if (remember) exp = Date.from(exp.toInstant().plusSeconds(jwtProperties.getExtended()));

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSigningKey())
                .compact();

        //jwtAuthToken.setToken(token);

        return token;
    }
}
