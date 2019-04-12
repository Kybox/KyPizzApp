import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {IUser} from "../../entity/account/user.model";
import {AppSettings} from "../../config/app.settings";
import {AuthenticationService} from "../authentication/authentication.service";
import {Address} from "../../entity/account/address.model";

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

    public getUserAddresses(): Promise<Address[]> {

        return this.http
            .get<Address[]>(AppSettings.ACCOUNT_API_URL + "addresses")
            .toPromise();
    }

    public createNewAddress(address:Address) :Promise<Address> {

        return this.http
            .post<Address>(AppSettings.ACCOUNT_API_URL + "address", address)
            .toPromise();
    }
}
