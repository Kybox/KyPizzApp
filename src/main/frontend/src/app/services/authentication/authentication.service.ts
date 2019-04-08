import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {RegisterForm} from "../../entity/form/register.form.model";
import {LoginForm} from "../../entity/form/login.form.model";
import {AppSettings} from "../../config/app.settings";
import {IJwtObject, JwtObject} from "../../entity/security/jwt.object.model";
import {IAuthenticated} from "../../entity/security/authenticated.model";
import {IJwtStorageKey} from "../../entity/security/jwt.storage.key.model";

type EntityResponseType = HttpResponse<IJwtObject>;

@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {

    constructor(private http: HttpClient) {
    }

    static saveToken(jwtToken: JwtObject, remember: boolean): void {

        if (remember) localStorage.setItem(AppSettings.JWT_STORAGE_KEY, jwtToken.token);
        else sessionStorage.setItem(AppSettings.JWT_STORAGE_KEY, jwtToken.token);
    }

    static clearToken(): void {

        localStorage.removeItem(AppSettings.JWT_STORAGE_KEY);
        sessionStorage.removeItem(AppSettings.JWT_STORAGE_KEY);
    }

    static getJwtObject(): JwtObject | null {

        let token: string = localStorage.getItem(AppSettings.JWT_STORAGE_KEY);
        if (token != null) return new JwtObject(token);
        else {
            token = sessionStorage.getItem(AppSettings.JWT_STORAGE_KEY);
            if (token != null) return new JwtObject(token);
        }
        return null;
    }

    register(registerForm: RegisterForm): Observable<EntityResponseType> {

        return this.http
            .post<IJwtObject>(AppSettings.PUBLIC_API + "register",
                registerForm, {observe: "response"});
    }

    login(loginForm: LoginForm): Observable<EntityResponseType> {

        return this.http
            .post<JwtObject>(AppSettings.PUBLIC_API + "login",
                loginForm, {observe: "response"});
    }

    isAuthorized() :Observable<HttpResponse<IAuthenticated>> {

        return this.http
            .get<IAuthenticated>(AppSettings.PUBLIC_API + "authenticated",
                {observe: "response"});
    }

    isAuthenticated(token: string): Observable<HttpResponse<IAuthenticated>> {

        if (token !== null) {

            return this.http
                .get<IAuthenticated>(AppSettings.PUBLIC_API + "authenticated",
                    {
                        observe: "response",
                        headers: new HttpHeaders({"Authorization": "Bearer " + token})
                    });
        }
    }

    getStorageKey(): Observable<HttpResponse<IJwtStorageKey>> {

        return this.http
            .get<IJwtStorageKey>(AppSettings.PUBLIC_API + "storage/key",
                {observe: "response"});
    }
}
