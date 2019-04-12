import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {Payment} from "../../entity/payment/payment.model";
import {AppSettings} from "../../config/app.settings";
import {PaypalPayUrl} from "../../entity/payment/paypal/paypal.pay.url.model";
import {Router} from "@angular/router";
import {PaypalRedirectUrls} from "../../entity/payment/paypal/paypal.redirect.urls.model";

@Injectable({
    providedIn: 'root'
})
export class PaymentService {

    constructor(private http: HttpClient, private router:Router) {
    }

    public findById(id: string): Observable<HttpResponse<Payment>> {

        return this.http
            .get<Payment>(AppSettings.ADMIN_PAYMENT_API + id,
                {observe: "response"});
    }

    public findAll(): Observable<HttpResponse<Payment[]>> {

        return this.http
            .get<Payment[]>(AppSettings.ADMIN_PAYMENT_API,
                {observe: "response"});
    }

    public create(payment: Payment): Observable<HttpResponse<Payment>> {

        return this.http
            .post<Payment>(AppSettings.ADMIN_PAYMENT_API,
                payment, {observe: "response"});
    }

    public update(payment: Payment): Observable<HttpResponse<Payment>> {

        return this.http
            .put<Payment>(AppSettings.ADMIN_PAYMENT_API,
                payment, {observe: "response"});
    }

    public delete(id:string) : Observable<HttpResponse<Payment[]>> {

        return this.http
            .delete<Payment[]>(AppSettings.ADMIN_PAYMENT_API + id,
                {observe : "response"});
    }

    public callPaymentApi(api:string, id:string) :void {

        console.log("api = " + api);

        if(api === "paypal"){
            let paypalRedirectUrls:PaypalRedirectUrls = new PaypalRedirectUrls(
                AppSettings.URL_PAYMENT_SUCCESS, AppSettings.URL_PAYMENT_FAILED);

            console.log("urls = " + paypalRedirectUrls);
            console.log("path = " + AppSettings.CUSTOMER_PAYMENT_API + "paypal/" + id)

            this.http.post(AppSettings.CUSTOMER_PAYMENT_API + "paypal/" + id, paypalRedirectUrls);
        }
    }
}
