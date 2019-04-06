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
