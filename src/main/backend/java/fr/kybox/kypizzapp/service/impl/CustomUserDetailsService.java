package fr.kybox.kypizzapp.service.impl;

import fr.kybox.kypizzapp.exception.UserNotActivatedException;
import fr.kybox.kypizzapp.model.auth.RegisteredUser;
import fr.kybox.kypizzapp.repository.RegisteredUserRepository;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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

    private final RegisteredUserRepository registredUserRepository;

    @Autowired
    public CustomUserDetailsService(RegisteredUserRepository registredUserRepository) {
        this.registredUserRepository = registredUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {

        if(new EmailValidator().isValid(login, null)){
            return registredUserRepository
                    .findOneByEmailIgnoreCase(login)
                    .map(user -> createSpringSecurityUser(login, user))
                    .orElseThrow(() -> new UsernameNotFoundException("User with this email " + login + " was not found"));
        }

        String lowerCaseLogin = login.toLowerCase(Locale.ENGLISH);
        return registredUserRepository
                .findOneByNickName(lowerCaseLogin)
                .map(user -> createSpringSecurityUser(lowerCaseLogin, user))
                .orElseThrow(() -> new UsernameNotFoundException("User with this nickname " + lowerCaseLogin + " was not found"));
    }

    private User createSpringSecurityUser(String login, RegisteredUser registredUser){

        if(!registredUser.isActivated())
            throw new UserNotActivatedException("User " + login + " was not activated");

        List<GrantedAuthority> grantedAuthorityList = registredUser
                .getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());

        return new User(registredUser.getNickName(), registredUser.getPassword(), grantedAuthorityList);
    }
}
