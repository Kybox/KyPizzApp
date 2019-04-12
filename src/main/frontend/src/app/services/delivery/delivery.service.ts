import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {DeliveryMethod} from "../../entity/order/delivery.method.model";
import {Observable} from "rxjs";
import {AppSettings} from "../../config/app.settings";

@Injectable({
    providedIn: 'root'
})
export class DeliveryService {

    constructor(private http: HttpClient) {
    }

    public create(deliveryMethod: DeliveryMethod): Observable<HttpResponse<DeliveryMethod>> {

        return this.http
            .post<DeliveryMethod>(AppSettings.ADMIN_DELIVERY_API,
                deliveryMethod, {observe: "response"});
    }

    public findAll(): Observable<HttpResponse<DeliveryMethod[]>> {

        return this.http
            .get<DeliveryMethod[]>(AppSettings.PUBLIC_DELIVERY_API + "all",
                {observe: "response"});
    }

    public findById(id: string): Observable<HttpResponse<DeliveryMethod>> {

        return this.http
            .get<DeliveryMethod>(AppSettings.PUBLIC_DELIVERY_API + id,
                {observe: "response"});
    }

    public update(deliveryMethod: DeliveryMethod): Observable<HttpResponse<DeliveryMethod>> {

        return this.http
            .put<DeliveryMethod>(AppSettings.ADMIN_DELIVERY_API,
                deliveryMethod, {observe: "response"});
    }

    public delete(id: string): Observable<HttpResponse<DeliveryMethod[]>> {

        return this.http
            .delete<DeliveryMethod[]>(AppSettings.ADMIN_DELIVERY_API + id,
                {observe: "response"});
    }
}
