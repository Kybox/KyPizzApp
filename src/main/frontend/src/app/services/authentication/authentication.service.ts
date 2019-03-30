import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {User} from "../../entity/account/user.model";
import {Observable} from "rxjs";
import {RegisterForm} from "../../entity/form/register.form.model";
import {LoginForm} from "../../entity/form/login.form.model";
import {AppSettings} from "../../config/app.settings";
import {IJwtToken, JwtToken} from "../../entity/security/jwt.model";
import {IAuthenticated} from "../../entity/security/authenticated.model";
import {IProduct} from "../../entity/product.model";

type EntityResponseType = HttpResponse<IJwtToken>;

@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {

    constructor(private http: HttpClient) {
    }

    register(registerForm: RegisterForm): Observable<EntityResponseType> {

        return this.http
            .post<IJwtToken>(AppSettings.PUBLIC_API_URL + "register",
                registerForm, {observe: "response"});
    }

    login(loginForm: LoginForm): Observable<EntityResponseType> {

        return this.http
            .post<JwtToken>(AppSettings.PUBLIC_API_URL + "login",
                loginForm, {observe: "response"});
    }

    saveToken(jwtToken: JwtToken, remember: boolean): void {

        if (remember) localStorage.setItem(AppSettings.JWT_STORAGE_KEY, jwtToken.token);
        else sessionStorage.setItem(AppSettings.JWT_STORAGE_KEY, jwtToken.token);
    }

    getToken(): JwtToken {

        let token: string = localStorage.getItem(AppSettings.JWT_STORAGE_KEY);
        console.log("local = " + token);

        return new JwtToken(token);
    }

    isAuthenticated(token: string): Observable<HttpResponse<IAuthenticated>> {

        if (token !== null) {

            return this.http
                .get<IAuthenticated>(AppSettings.PUBLIC_API_URL + "authenticated", {
                    observe: "response",
                    headers: new HttpHeaders({"Authorization": "Bearer " + token})
                });
        }
    }
}
