package fr.kybox.kypizzapp.exception;

import org.springframework.security.core.AuthenticationException;

public class UserEmailAlreadyExistsException extends AuthenticationException {

    public UserEmailAlreadyExistsException(String msg) {
        super(msg);
    }

    public UserEmailAlreadyExistsException(String msg, Throwable t) {
        super(msg, t);
    }
}
