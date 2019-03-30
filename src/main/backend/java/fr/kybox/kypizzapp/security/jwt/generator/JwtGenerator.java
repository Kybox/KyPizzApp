package fr.kybox.kypizzapp.security.jwt.generator;

import fr.kybox.kypizzapp.model.auth.Authority;
import fr.kybox.kypizzapp.model.auth.RegisteredUser;
import fr.kybox.kypizzapp.security.jwt.property.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

import static fr.kybox.kypizzapp.utils.constant.ValueObject.*;

@Component
public class JwtGenerator {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final JwtProperties jwtProperties;

    public JwtGenerator(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String generate(RegisteredUser registeredUser, boolean remember) {

        Claims claims = Jwts.claims().setSubject(registeredUser.getNickName());
        claims.put(JWT_ID, registeredUser.getId());

        String authorityList = registeredUser
                .getAuthorities()
                .stream()
                .map(Authority::getName)
                .collect(Collectors.joining(COMMA));

        claims.put(JWT_AUTHORITIES, authorityList);
        claims.put(JWT_ACTIVE, registeredUser.isActivated());

        Date exp = Date.from(Instant.now().plusSeconds(jwtProperties.getExpiration()));
        if (remember) exp = Date.from(exp.toInstant().plusSeconds(jwtProperties.getExtended()));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSigningKey())
                .compact();
    }
}
