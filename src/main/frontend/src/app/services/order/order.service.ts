import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Order} from "../../entity/order.model";
import {AppSettings} from "../../config/app.settings";

@Injectable({
    providedIn: 'root'
})
export class OrderService {

    constructor(private http: HttpClient) {
    }

    createNewOrder(): Promise<Order> {

        return this.http
            .get<Order>(AppSettings.CUSTOMER_ORDER_API + "create")
            .toPromise();
    }
}
