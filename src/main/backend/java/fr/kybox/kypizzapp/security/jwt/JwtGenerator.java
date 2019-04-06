package fr.kybox.kypizzapp.security.jwt;

import fr.kybox.kypizzapp.config.property.JwtProperties;
import fr.kybox.kypizzapp.security.jwt.model.JwtUser;
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

        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();

        Claims claims = Jwts.claims().setSubject(jwtUser.getUsername());
        claims.put(JWT_ID, jwtUser.getId());
        claims.put(JWT_ACTIVE, jwtUser.isActive());
        claims.put(JWT_AUTHORITIES, authorityList);

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
