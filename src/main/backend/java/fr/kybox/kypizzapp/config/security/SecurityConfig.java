package fr.kybox.kypizzapp.config.security;

import fr.kybox.kypizzapp.security.jwt.filter.JwtFilter;
import fr.kybox.kypizzapp.security.jwt.handler.JwtDeniedHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static fr.kybox.kypizzapp.security.Authorities.ADMIN;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final JwtFilter jwtFilter;
    private final JwtDeniedHandler jwtDeniedHandler;

    @Autowired
    public SecurityConfig(JwtFilter jwtFilter,
                          JwtDeniedHandler jwtDeniedHandler) {

        this.jwtFilter = jwtFilter;
        this.jwtDeniedHandler = jwtDeniedHandler;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/admin/**").hasAuthority(ADMIN)
                .and()
                .exceptionHandling().accessDeniedHandler(jwtDeniedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
