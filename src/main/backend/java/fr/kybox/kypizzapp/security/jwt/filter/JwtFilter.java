package fr.kybox.kypizzapp.security.jwt.filter;

import fr.kybox.kypizzapp.security.Authenticator;
import fr.kybox.kypizzapp.security.jwt.model.JwtAuthToken;
import fr.kybox.kypizzapp.config.property.JwtProperties;
import fr.kybox.kypizzapp.security.jwt.model.JwtUser;
import fr.kybox.kypizzapp.security.jwt.provider.JwtProvider;
import fr.kybox.kypizzapp.security.jwt.validator.JwtValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private JwtValidator jwtValidator;

    public JwtFilter(RequestMatcher requestMatcher) {
        super(requestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException, IOException {

        String authHeader = request.getHeader(AUTH_HEADER);

        if (authHeader == null || authHeader.isEmpty() || !authHeader.startsWith(jwtProperties.getPrefix())) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization failed : The token is missing !");
            return null;
        }

        //String token = authHeader.replace(jwtProperties.getPrefix() + SPACE, EMPTY);

        JwtUser jwtUser = jwtValidator.validate(authHeader);

        JwtAuthToken jwtAuthToken =
                new JwtAuthToken(
                        jwtUser.getUsername(),
                        "",
                        jwtUser.getAuthorities(),
                        jwtUser.getId());

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

    @Override
    protected AuthenticationManager getAuthenticationManager() {

        return new Authenticator(jwtProvider);
    }
}
