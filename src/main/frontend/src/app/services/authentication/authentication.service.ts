import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {IUser} from "../../entity/user.model";
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {

    constructor(private http: HttpClient) {
    }

    register(user: IUser): Observable<HttpResponse<IUser>> {

        return this.http
            .post<IUser>("http://localhost:8080/api/register",
                user, {observe: "response"});
    }

    login(user: IUser): Observable<HttpResponse<IUser>> {

        return this.http
            .post<IUser>("http://localhost:8080/api/login",
                user, {observe: "response"});
    }
}
