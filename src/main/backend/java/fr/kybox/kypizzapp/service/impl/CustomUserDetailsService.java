package fr.kybox.kypizzapp.service.impl;

import fr.kybox.kypizzapp.exception.UserNotActivatedException;
import fr.kybox.kypizzapp.model.auth.RegisteredUser;
import fr.kybox.kypizzapp.repository.RegisteredUserRepository;
import fr.kybox.kypizzapp.security.jwt.model.JwtUser;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @Override
    public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {

        log.info("loadUserByUsername");
        if (new EmailValidator().isValid(login, null)) {
            return registeredUserRepository
                    .findFirstByEmail(login)
                    .map(user -> createSpringSecurityUser(login, user))
                    .orElseThrow(() -> new UsernameNotFoundException("User with this email " + login + " was not found"));
        }

        String lowerCaseLogin = login.toLowerCase(Locale.ENGLISH);
        return registeredUserRepository
                .findFirstByNickNameIgnoreCase(lowerCaseLogin)
                .map(user -> createSpringSecurityUser(lowerCaseLogin, user))
                .orElseThrow(() -> new UsernameNotFoundException("User with this nickname " + lowerCaseLogin + " was not found"));
    }

    private JwtUser createSpringSecurityUser(String login, RegisteredUser user) {

        if (!user.isActivated())
            throw new UserNotActivatedException("User " + login + " was not activated");

        List<GrantedAuthority> grantedAuthorityList = user
                .getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());

        return new JwtUser(user.getNickName(), user.getPassword(), grantedAuthorityList, user.getId(), user.isActivated());
    }
}
