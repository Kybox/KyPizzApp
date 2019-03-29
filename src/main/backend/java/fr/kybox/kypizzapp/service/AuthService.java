package fr.kybox.kypizzapp.service;

import fr.kybox.kypizzapp.model.auth.LoginForm;
import fr.kybox.kypizzapp.model.auth.RegisterForm;
import fr.kybox.kypizzapp.model.auth.RegisteredUser;

public interface AuthService {

    RegisteredUser registerUser(RegisterForm registerForm);
    RegisteredUser loginUser(LoginForm loginForm);
}
