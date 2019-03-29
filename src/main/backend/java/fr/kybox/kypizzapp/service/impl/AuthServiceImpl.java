package fr.kybox.kypizzapp.service.impl;

import fr.kybox.kypizzapp.exception.UserEmailAlreadyExistsException;
import fr.kybox.kypizzapp.model.auth.Authority;
import fr.kybox.kypizzapp.model.auth.LoginForm;
import fr.kybox.kypizzapp.model.auth.RegisterForm;
import fr.kybox.kypizzapp.model.auth.RegisteredUser;
import fr.kybox.kypizzapp.repository.AuthorityRepository;
import fr.kybox.kypizzapp.repository.RegisteredUserRepository;
import fr.kybox.kypizzapp.security.Authorities;
import fr.kybox.kypizzapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RegisteredUserRepository registeredUserRepository;
    private final AuthorityRepository authorityRepository;

    @Autowired
    public AuthServiceImpl(RegisteredUserRepository registeredUserRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder, AuthorityRepository authorityRepository) {

        this.registeredUserRepository = registeredUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public RegisteredUser registerUser(RegisterForm registerForm) {

        registeredUserRepository
                .findOneByEmailIgnoreCase(registerForm.getEmail())
                .ifPresent(email -> new UserEmailAlreadyExistsException("This email (" + email + ") already exists"));

        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setNickName(registerForm.getNickName());
        registeredUser.setFirstName(registerForm.getFirstName());
        registeredUser.setLastName(registerForm.getLastName());
        registeredUser.setEmail(registerForm.getEmail());
        registeredUser.setPassword(bCryptPasswordEncoder.encode(registerForm.getPassword()));
        registeredUser.setActivated(true);
        registeredUser.setAuthorities(Collections.singletonList(authorityRepository.findFirstByName(Authorities.CUSTOMER)));
        registeredUser.setCreationDate(LocalDateTime.now());

        return registeredUserRepository.save(registeredUser);
    }

    @Override
    public RegisteredUser loginUser(LoginForm loginForm) {
        return null;
    }
}
