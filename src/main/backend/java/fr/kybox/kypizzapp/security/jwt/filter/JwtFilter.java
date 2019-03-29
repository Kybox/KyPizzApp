package fr.kybox.kypizzapp.security.jwt.filter;

import fr.kybox.kypizzapp.security.jwt.model.JwtAuthToken;
import fr.kybox.kypizzapp.security.jwt.property.JwtProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static fr.kybox.kypizzapp.utils.constant.ValueObject.*;

@Component
public class JwtFilter extends AbstractAuthenticationProcessingFilter {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JwtProperties jwtProperties;

    public JwtFilter(RequestMatcher requestMatcher) {
        super(requestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {

        String authHeader = request.getHeader(AUTH_HEADER);

        if (authHeader == null || authHeader.isEmpty() || !authHeader.startsWith(jwtProperties.getPrefix())) {

            log.warn("Authorization failed : The token is missing !");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

        String token = authHeader.replace(jwtProperties.getPrefix() + SPACE, EMPTY);
        JwtAuthToken jwtAuthToken = new JwtAuthToken(token);

        return getAuthenticationManager().authenticate(jwtAuthToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain,
                                            Authentication authentication)
            throws IOException, ServletException {

        super.successfulAuthentication(request, response, filterChain, authentication);

        log.info("Successful authentication");

        filterChain.doFilter(request, response);
    }
}
