package fr.kybox.kypizzapp.security.jwt.provider;

import fr.kybox.kypizzapp.exception.LoginFormException;
import fr.kybox.kypizzapp.model.auth.Authority;
import fr.kybox.kypizzapp.model.auth.RegisteredUser;
import fr.kybox.kypizzapp.repository.RegisteredUserRepository;
import fr.kybox.kypizzapp.security.jwt.model.JwtAuthToken;
import fr.kybox.kypizzapp.security.jwt.model.JwtUser;
import fr.kybox.kypizzapp.security.jwt.model.JwtUserDetails;
import fr.kybox.kypizzapp.security.jwt.validator.JwtValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JwtProvider extends AbstractUserDetailsAuthenticationProvider {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
    public Authentication authenticate(Authentication authentication) throws LoginFormException {

        RegisteredUser user = searchUserByLogin(authentication.getName());

        if(!bCryptPasswordEncoder.matches((CharSequence) authentication.getCredentials(), user.getPassword()))
            throw new LoginFormException("Bad password.");

        Collection<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        user.getAuthorities().forEach(a -> authorityList.add(new SimpleGrantedAuthority(a.getName())));

        return new JwtAuthToken(user.getNickName(), user.getPassword(), authorityList, user.getId());
    }

    @Override
    protected UserDetails retrieveUser(String username,
                                       UsernamePasswordAuthenticationToken userPassAuthToken)
            throws AuthenticationException {

        log.info("Retrieve user");

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

    private RegisteredUser searchUserByLogin(String login) throws LoginFormException {

        Optional<RegisteredUser> optUser = registeredUserRepository
                .findFirstByEmail(login);

        if(optUser.isPresent()) return optUser.get();

        optUser = registeredUserRepository
                .findFirstByNickNameIgnoreCase(login);

        if(optUser.isPresent()) return optUser.get();

        throw new LoginFormException("Login unknown.");
    }
}
