package fr.kybox.kypizzapp.security.config;

import fr.kybox.kypizzapp.config.property.JwtProperties;
import fr.kybox.kypizzapp.security.jwt.JwtFilter;
import fr.kybox.kypizzapp.security.jwt.JwtProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private JwtProperties jwtProperties;
    private JwtProvider jwtProvider;

    JwtConfig(JwtProperties jwtProperties, JwtProvider jwtProvider) {
        this.jwtProperties = jwtProperties;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {

        JwtFilter jwtFilter = new JwtFilter(jwtProperties, jwtProvider);
        builder.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
