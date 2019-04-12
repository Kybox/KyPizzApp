import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Order} from "../../entity/order.model";
import {AppSettings} from "../../config/app.settings";
import {Cart} from "../../entity/cart/cart.model";
import {OrderProduct} from "../../entity/order/order.product.model";

@Injectable({
    providedIn: 'root'
})
export class OrderService {

    constructor(private http: HttpClient) {
    }

    public createNewOrder(): Promise<Order> {

        return this.http
            .get<Order>(AppSettings.CUSTOMER_ORDER_API + "create")
            .toPromise();
    }

    public getLastSavedOrder() :Promise<Order> {

        return this.http
            .get<Order>(AppSettings.CUSTOMER_ORDER_API + "last/saved")
            .toPromise();
    }

    public updateOrderFormCart(cart:Cart, order:Order): Promise<Order> {

        let productList:OrderProduct[] = [];

        for(let item of cart.productList) {
            let product: OrderProduct = new OrderProduct(item.id, item.quantity);
            productList.push(product);
        }

        order.productList = productList;

        return this.http
            .put<Order>(AppSettings.CUSTOMER_ORDER_API + "update", order)
            .toPromise();
    }

    public updateOrder(order:Order) : Promise<Order> {

        return this.http
            .put<Order>(AppSettings.CUSTOMER_ORDER_API + "update", order)
            .toPromise();
    }
}
