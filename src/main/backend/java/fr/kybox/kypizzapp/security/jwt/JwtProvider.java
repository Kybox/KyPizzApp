package fr.kybox.kypizzapp.security.jwt;

import fr.kybox.kypizzapp.config.property.JwtProperties;
import fr.kybox.kypizzapp.security.jwt.JwtGenerator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static fr.kybox.kypizzapp.utils.constant.ValueObject.*;

@Component
public class JwtProvider {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private JwtProperties jwtProperties;

    public String generateToken(Authentication authentication, boolean remember) {

        log.info("JwtProvider > generateToken");
        return jwtGenerator.generate(authentication, remember);
    }

    public Authentication getAuthentication(String token) {

        log.info("JwtProvider > getAuthentication");

        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperties.getSigningKey())
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(JWT_AUTHORITIES).toString().split(COMMA))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), EMPTY, authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token) {

        log.info("JwtProvider > validateToken");

        try {

            Jwts.parser()
                    .setSigningKey(jwtProperties.getSigningKey())
                    .parseClaimsJws(token);

            return true;
        }
        catch (Exception e) {

            log.warn("Validate token :");
            log.warn(e.getMessage());
        }

        return false;
    }
}

/*
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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        log.info("Name = " + auth.getName());
        log.info("Cred = " + auth.getCredentials().toString());
        log.info("Principal = " + auth.getPrincipal());

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
*/
