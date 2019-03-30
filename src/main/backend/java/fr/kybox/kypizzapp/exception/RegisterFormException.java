package fr.kybox.kypizzapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class RegisterFormException extends RuntimeException {

    public RegisterFormException(String msg) {
        super(msg);
    }
}
