import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {CartService} from "../../../../services/cart/cart.service";
import {Cart} from "../../../../entity/cart/cart.model";
import {OrderService} from "../../../../services/order/order.service";
import {Order} from "../../../../entity/order.model";
import {Product} from "../../../../entity/product.model";
import {ProductService} from "../../../../services/product/product.service";
import {AppSettings} from "../../../../config/app.settings";
import {ProductDetails} from "../../../../entity/order/product.details.model";
import {CartProduct} from "../../../../entity/cart/cart.product.model";

@Component({
    selector: 'app-order',
    templateUrl: './order.component.html',
    styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

    public process: string;

    public order: Order;
    public totalItems: number;
    public totalAmount: number;
    public productDetails: ProductDetails[];
    public productsLoaded: boolean;

    constructor(private route: ActivatedRoute,
                private cartService: CartService,
                private orderService: OrderService,
                private productService: ProductService,
                private router: Router) {

        this.totalItems = 0;
        this.totalAmount = 0;
        this.productDetails = [];

        this.process = atob(this.route.snapshot.paramMap.get("process"));
    }

    ngOnInit() {

        this.createNewOrder();
    }

    public getPriceInclTax(price: number, tax: number, quantity: number): number {

        return (price * (1 + (tax / 100))) * quantity;
    }

    public setOrderDetails(): void {

        let items: number = 0;
        let total: number = 0;

        if (this.productDetails != undefined)
            for (let item of this.productDetails) {
                items += item.quantity;
                total += item.amount;
            }

        this.totalItems = items;
        this.totalAmount = total;
    }

    public updateQuantity(evt): void {

        let id: number = evt.target.id;
        let quantity: number = evt.target.value;

        this.productDetails[id].quantity = quantity;
        this.productDetails[id].amount =
            this.getPriceInclTax(
                this.productDetails[id].unitPrice,
                this.productDetails[id].tax,
                quantity);

        this.setOrderDetails();
        this.cartService.updateCart(this.productDetails);
    }

    public removeItem(evt): void {

        let index: number = evt.currentTarget.name;
        this.productDetails.splice(index, 1);
        this.setOrderDetails();
        this.cartService.updateCart(this.productDetails);
    }

    private createNewOrder(): void {

        this.orderService.createNewOrder().then(
            resp => {
                this.order = resp;
                this.getAllProducts();
            },
            error => {
                //console.log(error);
                if (error.status === AppSettings.HTTP_CODE_UNAUTHORIZED)
                    this.router.navigateByUrl("auth/login/" + btoa("cart")).then(null);
                else this.productsLoaded = true;
            }
        );
    }

    private getAllProducts(): void {

        Object.keys(this.order.productList).map(key => {

            this.productService.findById(key).then(
                resp => {
                    let product: Product = resp;
                    let quantity: number = this.order.productList[key];
                    let productDetails: ProductDetails = new ProductDetails(
                        product,
                        quantity,
                        product.price,
                        product.tax,
                        this.getPriceInclTax(product.price, product.tax, quantity));
                    this.productDetails.push(productDetails);

                    if (this.productDetails.length === Object.keys(this.order.productList).length) {
                        this.setOrderDetails();
                        this.productsLoaded = true;
                    }
                },
                error => console.log(error)
            );
        });
    }

    public validateProcess(id:number) :void {

        this.process = "process" + (id + 1);
    }
}
