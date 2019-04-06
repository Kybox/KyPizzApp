package fr.kybox.kypizzapp.service;

import fr.kybox.kypizzapp.model.auth.Authenticated;
import fr.kybox.kypizzapp.model.auth.LoginForm;
import fr.kybox.kypizzapp.model.auth.RegisterForm;
import fr.kybox.kypizzapp.model.auth.RegisteredUser;
import fr.kybox.kypizzapp.security.jwt.model.JwtToken;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

    RegisteredUser registerUser(RegisterForm registerForm);
    JwtToken loginUser(LoginForm loginForm);
    Authenticated isAuthenticated(HttpServletRequest request);
    RegisteredUser getAuthenticatedUser(HttpServletRequest request);
}
