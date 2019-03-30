package fr.kybox.kypizzapp.service.impl;

import fr.kybox.kypizzapp.exception.LoginFormException;
import fr.kybox.kypizzapp.exception.RegisterFormException;
import fr.kybox.kypizzapp.model.auth.*;
import fr.kybox.kypizzapp.repository.AuthorityRepository;
import fr.kybox.kypizzapp.repository.RegisteredUserRepository;
import fr.kybox.kypizzapp.security.Authorities;
import fr.kybox.kypizzapp.security.jwt.generator.JwtGenerator;
import fr.kybox.kypizzapp.security.jwt.model.JwtToken;
import fr.kybox.kypizzapp.security.jwt.property.JwtProperties;
import fr.kybox.kypizzapp.security.jwt.validator.JwtValidator;
import fr.kybox.kypizzapp.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RegisteredUserRepository registeredUserRepository;
    private final AuthorityRepository authorityRepository;
    private final JwtGenerator jwtGenerator;
    private final JwtProperties jwtProperties;
    private final JwtValidator jwtValidator;

    @Autowired
    public AuthServiceImpl(RegisteredUserRepository registeredUserRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           AuthorityRepository authorityRepository,
                           JwtGenerator jwtGenerator,
                           JwtProperties jwtProperties, JwtValidator jwtValidator) {

        this.registeredUserRepository = registeredUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authorityRepository = authorityRepository;
        this.jwtGenerator = jwtGenerator;
        this.jwtProperties = jwtProperties;
        this.jwtValidator = jwtValidator;
    }

    @Override
    public RegisteredUser registerUser(RegisterForm registerForm) {

        Optional<RegisteredUser> optUser = registeredUserRepository
                .findFirstByEmail(registerForm.getEmail());

        if(optUser.isPresent())
            throw new RegisterFormException("This email already exists.");

        optUser = registeredUserRepository
                .findFirstByNickNameIgnoreCase(registerForm.getNickName());

        if(optUser.isPresent())
            throw new RegisterFormException(("This nickname already exists."));

        Authority authority = authorityRepository.findFirstByName(Authorities.CUSTOMER);
        log.info("Authority = " + Arrays.toString(authorityRepository.findAll().toArray()));

        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setNickName(registerForm.getNickName());
        registeredUser.setFirstName(registerForm.getFirstName());
        registeredUser.setLastName(registerForm.getLastName());
        registeredUser.setEmail(registerForm.getEmail());
        registeredUser.setPassword(bCryptPasswordEncoder.encode(registerForm.getPassword1()));
        registeredUser.setActivated(true);
        registeredUser.setAuthorities(Collections.singletonList(authority));
        registeredUser.setCreationDate(LocalDateTime.now());

        log.info(registeredUser.toString());

        return registeredUserRepository.save(registeredUser);
    }

    @Override
    public JwtToken loginUser(LoginForm loginForm) {

        RegisteredUser user = searchUserByLogin(loginForm.getLogin());

        if(!bCryptPasswordEncoder.matches(loginForm.getPassword(), user.getPassword()))
            throw new LoginFormException("Bad password.");

        return new JwtToken(jwtGenerator.generate(user, loginForm.isRemember()));
    }

    @Override
    public Authenticated isAuthenticated(HttpServletRequest request) {

        String token = request.getHeader(jwtProperties.getHeader());
        if(token == null || token.isEmpty()) return new Authenticated(false);

        return new Authenticated(jwtValidator.validate(token) != null);
    }

    private RegisteredUser searchUserByLogin(String login){

        Optional<RegisteredUser> optUser = registeredUserRepository
                .findFirstByEmail(login);

        if(optUser.isPresent()) return optUser.get();

        optUser = registeredUserRepository
                .findFirstByNickNameIgnoreCase(login);

        if(optUser.isPresent()) return optUser.get();

        throw new LoginFormException("Login unknown.");
    }
}
