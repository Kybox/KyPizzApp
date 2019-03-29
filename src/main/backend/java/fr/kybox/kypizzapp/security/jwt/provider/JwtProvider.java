package fr.kybox.kypizzapp.security.jwt.provider;

import fr.kybox.kypizzapp.security.jwt.model.JwtAuthToken;
import fr.kybox.kypizzapp.security.jwt.model.JwtUser;
import fr.kybox.kypizzapp.security.jwt.model.JwtUserDetails;
import fr.kybox.kypizzapp.security.jwt.validator.JwtValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider extends AbstractUserDetailsAuthenticationProvider {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final JwtValidator jwtValidator;

    public JwtProvider(JwtValidator jwtValidator) {
        this.jwtValidator = jwtValidator;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken)
            throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username,
                                       UsernamePasswordAuthenticationToken userPassAuthToken)
            throws AuthenticationException {

        JwtAuthToken jwtAuthToken = (JwtAuthToken) userPassAuthToken;
        String currentToken = jwtAuthToken.getToken();

        JwtUser jwtUser = jwtValidator.validate(currentToken);
        if (jwtUser == null) return null;

        return new JwtUserDetails(
                jwtUser.getId(),
                jwtUser.getUsername(),
                currentToken,
                jwtUser.isActive(),
                jwtUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> supportClass) {

        return (JwtAuthToken.class.isAssignableFrom(supportClass));
    }
}
