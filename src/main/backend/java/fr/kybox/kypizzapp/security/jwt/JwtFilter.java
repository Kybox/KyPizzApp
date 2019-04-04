package fr.kybox.kypizzapp.security.jwt;

import fr.kybox.kypizzapp.config.property.JwtProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static fr.kybox.kypizzapp.utils.constant.ValueObject.SPACE;

@Component
public class JwtFilter extends GenericFilterBean {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private JwtProperties jwtProperties;
    private JwtProvider jwtProvider;

    public JwtFilter(JwtProperties jwtProperties, JwtProvider jwtProvider) {
        this.jwtProperties = jwtProperties;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        log.info("JwtFiler > doFilter");

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String jwt = resolveToken(httpServletRequest);

        log.info("JwtFilter > token : " + jwt);

        if (StringUtils.hasText(jwt) && this.jwtProvider.validateToken(jwt)) {

            Authentication authentication = this.jwtProvider.getAuthentication(jwt);

            log.info("JwtFilter > Principal : " + authentication.getPrincipal().toString());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        log.info("JwtFilter > Called from " + httpServletRequest.getRequestURL().toString());

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String resolveToken(HttpServletRequest request) {

        log.info("JwtFiler > resolveToken");

        String bearerToken = request.getHeader(jwtProperties.getHeader());

        log.info("JwtFilter / resolveToken : " + bearerToken);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtProperties.getPrefix() + SPACE))
            return bearerToken.substring(7);

        return null;
    }
}
/*
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

        log.info("JwtUser : " + jwtUser.toString());

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
}
*/
