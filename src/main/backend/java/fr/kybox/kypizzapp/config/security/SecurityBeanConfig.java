package fr.kybox.kypizzapp.config.security;

import fr.kybox.kypizzapp.security.jwt.filter.JwtFilter;
import fr.kybox.kypizzapp.security.jwt.handler.JwtSuccessHandler;
import fr.kybox.kypizzapp.security.jwt.property.JwtProperties;
import fr.kybox.kypizzapp.security.jwt.provider.JwtProvider;
import fr.kybox.kypizzapp.security.jwt.validator.JwtValidator;
import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class SecurityBeanConfig {

    @Bean
    public JwtProperties jwtProperties() {
        return new JwtProperties();
    }

    @Bean
    public JwtValidator jwtValidator() {
        return new JwtValidator();
    }

    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider(jwtValidator());
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(jwtProvider()));
    }

    @Bean
    public RequestMatcher requestMatcher() {

        List<RequestMatcher> requestMatcherList = new ArrayList<>();
        return new OrRequestMatcher(requestMatcherList);
    }

    @Bean
    public JwtFilter jwtFilter() {

        JwtFilter jwtFilter = new JwtFilter(requestMatcher());
        jwtFilter.setAuthenticationManager(authenticationManager());
        jwtFilter.setAuthenticationSuccessHandler(new JwtSuccessHandler());
        return jwtFilter;
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessor() {
        return (serverFactory) -> serverFactory.addContextCustomizers(
                (context) -> context.setCookieProcessor(new LegacyCookieProcessor()));
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
