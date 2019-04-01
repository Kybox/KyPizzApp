import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {AppSettings} from "../../config/app.settings";
import {ICartKey} from "../../entity/cart/cart.key.model";

@Injectable({
    providedIn: 'root'
})
export class CartService {

    constructor(private http:HttpClient) {
    }

    getKey():Observable<HttpResponse<ICartKey>> {

        return this.http
            .get<ICartKey>(AppSettings.PUBLIC_API_URL + "cart/key", {observe: "response"});
    }
}
