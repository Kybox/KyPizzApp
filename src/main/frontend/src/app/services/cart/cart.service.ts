import {Injectable} from '@angular/core';
import {Observable, Subject} from "rxjs";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {AppSettings} from "../../config/app.settings";
import {IGenericObject} from "../../entity/generic.object.model";
import {CartProduct, ICartProduct} from "../../entity/cart/cart.product.model";
import {Cart, ICart} from "../../entity/cart/cart.model";
import {ProductDetails} from "../../entity/order/product.details.model";
import {Order} from "../../entity/order.model";

@Injectable({
    providedIn: 'root'
})
export class CartService {

    public cart: Cart;
    public cartSubject: Subject<ICart>;

    constructor(private http: HttpClient) {


        this.cart = new Cart(null);
        this.cartSubject = new Subject();

    }


    public getKey(): Observable<HttpResponse<IGenericObject>> {

        return this.http
            .get<IGenericObject>(AppSettings.PUBLIC_API + "cart/key", {observe: "response"});
    }


    public addProduct(cartProduct: ICartProduct): Observable<HttpResponse<string>> {

        return this.http.post<string>(AppSettings.PUBLIC_API + "cart/add", cartProduct, {observe: "response"});
    }

    public getCart(): Cart {

        this.http.get<IGenericObject>(AppSettings.PUBLIC_API + "cart").subscribe(
            resp => {
                this.cart = JSON.parse(resp.data);
                this.cartSubject.next(this.cart);
            },
            error => console.log(error)
        );

        return this.cart;
    }

    getTotalItems(): number {

        let total: number = 0;

        if (this.cart !== null && this.cart.productList !== null) {
            for (let product of this.cart.productList)
                total += product.quantity;
        }

        return total;
    }

    public updateCartFromOrderProcess(productDetails:ProductDetails[]): void {

        let productList: CartProduct[] = [];
        let cart: Cart = new Cart(productList);

        for (let item of productDetails) {

            let product: CartProduct = new CartProduct(
                item.product.id,
                item.product.name,
                item.quantity
            );

            productList.push(product);
        }

        this.http
            .post<Cart>(AppSettings.PUBLIC_API + "cart/update", cart).toPromise().then(
                resp => {
                    this.cart = resp;
                    this.cartSubject.next(this.cart);
                }
            );
    }
}
