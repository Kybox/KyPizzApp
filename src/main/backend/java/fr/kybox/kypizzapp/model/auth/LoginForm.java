package fr.kybox.kypizzapp.model.auth;

import javax.validation.constraints.NotNull;

public class LoginForm {

    @NotNull
    private String login;

    @NotNull
    private String password;

    private boolean remember;

    public LoginForm() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    @Override
    public String toString() {
        return "LoginForm{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", remember=" + remember +
                '}';
    }
}
