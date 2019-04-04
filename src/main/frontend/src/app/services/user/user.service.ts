import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {IUser} from "../../entity/account/user.model";
import {AppSettings} from "../../config/app.settings";
import {IGenericObject} from "../../entity/generic.object.model";

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(private http: HttpClient) {
    }

    countAllUsers(): Observable<HttpResponse<Number>> {

        return this.http
            .get<Number>(AppSettings.ADMIN_USER_API + "count",
                {observe: "response"});
    }

    searchUser(data: IGenericObject): Observable<HttpResponse<IUser[]>> {

        return this.http
            .post<IUser[]>(
                AppSettings.ADMIN_USER_API + "search",
                data, {observe: "response"});
    }
}
