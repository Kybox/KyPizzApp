import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {IUser} from "../../entity/account/user.model";
import {AppSettings} from "../../config/app.settings";
import {AuthenticationService} from "../authentication/authentication.service";

@Injectable({
    providedIn: 'root'
})
export class AccountService {

    constructor(private http:HttpClient, private authService:AuthenticationService) {
    }

    getUserDetails():Observable<HttpResponse<IUser>> {

        return this.http
            .get<IUser>(AppSettings.ACCOUNT_API_URL + "user/details",
                {
                    observe: "response",
                    headers: new HttpHeaders(
                        {"Authorization": "Bearer " + AuthenticationService.getJwtObject().token})
                });
    }
}
