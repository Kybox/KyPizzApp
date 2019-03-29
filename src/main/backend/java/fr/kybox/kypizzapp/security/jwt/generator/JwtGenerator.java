package fr.kybox.kypizzapp.security.jwt.generator;

import fr.kybox.kypizzapp.security.jwt.model.JwtUser;
import fr.kybox.kypizzapp.security.jwt.property.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;
import java.util.stream.Collectors;

import static fr.kybox.kypizzapp.utils.constant.ValueObject.*;

@Component
public class JwtGenerator {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final JwtProperties jwtProperties;

    public JwtGenerator(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String generate(JwtUser jwtUser) {

        Claims claims = Jwts.claims().setSubject(jwtUser.getUsername());
        claims.put(JWT_ID, jwtUser.getId());

        String authorityList = jwtUser
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(COMMA));

        claims.put(JWT_AUTHORITIES, authorityList);
        claims.put(JWT_ACTIVE, jwtUser.isActive());

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now()
                        .plusSeconds(jwtProperties.getExpiration())))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSigningKey())
                .compact();

        return jwtProperties.getPrefix() + SPACE + token;
    }
}
