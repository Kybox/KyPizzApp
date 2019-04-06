package fr.kybox.kypizzapp.security.jwt;

import fr.kybox.kypizzapp.security.jwt.model.JwtUser;
import fr.kybox.kypizzapp.config.property.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static fr.kybox.kypizzapp.utils.constant.ValueObject.*;

@Component
public class JwtValidator {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JwtProperties jwtProperties;

    public JwtUser validate(String token) {

        log.info("JwtValidator > validate");

        JwtUser jwtUser = null;

        if(token.startsWith(jwtProperties.getPrefix()))
            token = token.replace(jwtProperties.getPrefix() + SPACE, EMPTY);

        try {

            Claims claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getSigningKey())
                    .parseClaimsJws(token).getBody();

            Collection<? extends GrantedAuthority> authorityList = Arrays
                    .stream(claims.get(JWT_AUTHORITIES).toString().split(COMMA))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            jwtUser = new JwtUser(claims.getSubject(), EMPTY, authorityList, (String) claims.get(JWT_ID), (Boolean) claims.get(JWT_ACTIVE));
        }
        catch (Exception e) { log.warn("JwtValidator error : " + e.getMessage()); }

        return jwtUser;
    }
}
