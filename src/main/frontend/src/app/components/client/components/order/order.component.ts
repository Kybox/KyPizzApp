import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {CartService} from "../../../../services/cart/cart.service";
import {OrderService} from "../../../../services/order/order.service";
import {Order} from "../../../../entity/order.model";
import {Product} from "../../../../entity/product.model";
import {ProductService} from "../../../../services/product/product.service";
import {AppSettings} from "../../../../config/app.settings";
import {ProductDetails} from "../../../../entity/order/product.details.model";
import {Address} from "../../../../entity/account/address.model";
import {AccountService} from "../../../../services/account/account.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Cart} from "../../../../entity/cart/cart.model";
import {AuthenticationService} from "../../../../services/authentication/authentication.service";
import {PaymentService} from "../../../../services/payment/payment.service";
import {Payment} from "../../../../entity/payment/payment.model";
import {DeliveryMethod} from "../../../../entity/order/delivery.method.model";
import {DeliveryService} from "../../../../services/delivery/delivery.service";

@Component({
    selector: 'app-order',
    templateUrl: './order.component.html',
    styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

    public process: string;

    public cart:Cart;
    public order: Order;
    public totalItems: number;
    public totalAmount: number;
    public productDetails: ProductDetails[];
    public addressList: Address[];
    public addressForm: FormGroup;
    public addressSelected:Address;
    public deliveryMethodList:DeliveryMethod[];
    public paymentList:Payment[];

    public productsLoaded: boolean;
    public addressesLoaded: boolean;
    public addNewAddress:boolean;

    constructor(private route: ActivatedRoute,
                private cartService: CartService,
                private orderService: OrderService,
                private productService: ProductService,
                private accountService: AccountService,
                private authService:AuthenticationService,
                private paymentService:PaymentService,
                private deliveryService:DeliveryService,
                private router: Router,
                private formBuilder: FormBuilder) {

        this.totalItems = 0;
        this.totalAmount = 0;
        this.productDetails = [];
        this.addressList = [];

        this.process = atob(this.route.snapshot.paramMap.get("process"));
    }

    ngOnInit() {

        this.authService.isAuthorized().subscribe(
            resp => {
                if(!resp.body.authenticated)
                    this.router.navigateByUrl("auth/login/" + btoa(this.process)).then(null);
                else {
                    this.cart = this.cartService.cart;
                    this.listenCart();
                    if (this.process === "process1") this.createNewOrder();
                    else {
                        if(this.order === undefined){
                            this.orderService.getLastSavedOrder().then(
                                (resp: Order) => {
                                    this.order = resp;
                                    if (this.process === "process2") this.buildAddressForm();
                                    if (this.process === "process3") this.loadDeliveryMethodList();
                                    if (this.process === "process4") this.loadPaymentList();
                                    if (this.process === "process5") this.loadAllProducts();
                                    },
                                    error => {
                                    console.log(error);
                                    this.router
                                        .navigateByUrl("auth/login/" + btoa("process1"))
                                        .then(null);
                            });
                        }
                        else console.log("order => " + JSON.stringify(this.order));
                    }
                }
            }
        );
    }

    private listenCart():void {

        this.cartService.cartSubject.subscribe(
            resp => this.cart = resp,
            error => console.log(error)
        );
    }

    private getLastSavedOrder() :void {

        this.orderService
            .getLastSavedOrder()
            .then(
                (resp: Order) => this.order = resp,
                error => {
                    console.log(error);
                    this.router.navigateByUrl("auth/login/" + btoa("process1")).then(null);
                });
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

        this.cartService.updateCartFromOrderProcess(this.productDetails);
    }

    public removeItem(evt): void {

        let index: number = evt.currentTarget.name;
        this.productDetails.splice(index, 1);
        this.setOrderDetails();

        this.cartService.updateCartFromOrderProcess(this.productDetails);
    }

    public validateProcess(id: number): void {

        if(id === 1) this.orderService.updateOrderFormCart(this.cart, this.order).then((resp:Order) => this.order = resp);
        if(id > 1) this.orderService.updateOrder(this.order).then((resp:Order) => {this.order = resp; console.log(this.order);});

        this.process = "process" + (id + 1);
        this.router.navigateByUrl("order/" + btoa(this.process)).then(null);
    }

    public setSelectAddress(index:number):void {

        this.addressSelected = this.addressList[index];
        this.order.address = this.addressSelected;
    }

    public setDeliveryMethod(idx:number) :void {

        this.order.deliveryMethod = this.deliveryMethodList[idx];
    }

    private createNewOrder(): void {

        this.orderService.createNewOrder().then(
            resp => {
                this.order = resp;
                this.loadAllProducts();
            },
            error => {
                //console.log(error);
                if (error.status === AppSettings.HTTP_CODE_UNAUTHORIZED)
                    this.router.navigateByUrl("auth/login/" + btoa("process1")).then(null);
                else this.productsLoaded = true;
            }
        );
    }

    private loadAllProducts(): void {

        for(let item of this.order.productList){

            this.productService.findById(item.id).then(
                resp => {
                    let product:Product = resp;
                    let quantity:number = item.quantity;
                    let productDetails: ProductDetails = new ProductDetails(
                        product,
                        quantity,
                        product.price,
                        product.tax,
                        this.getPriceInclTax(product.price, product.tax, quantity));
                    this.productDetails.push(productDetails);

                    if(this.productDetails.length === this.order.productList.length){
                        this.setOrderDetails();
                        this.productsLoaded = true;
                    }
                },
                error => console.log(error)
            );
        }
    }

    private buildAddressForm(): void {

        this.addressForm = this.formBuilder.group({
            firstName: [null, Validators.required],
            lastName: [null, Validators.required],
            streetNumber: [null, Validators.required],
            streetName: [null, Validators.required],
            zipCode: [null, Validators.required],
            city: [null, Validators.required],
            country: [null, Validators.required],
            additionalInfo: [null]
        });

        this.loadAddresses();
    }

    private loadAddresses(): void {

        this.accountService.getUserAddresses().then(
            resp => {
                this.addressList = resp;
                this.addressesLoaded = true;
                this.addressSelected = this.addressList[0];
                this.order.address = this.addressSelected;
            },
            error => {
                //console.log(error);
                if (error.status === AppSettings.HTTP_CODE_UNAUTHORIZED)
                    this.router.navigateByUrl("auth/login/" + btoa("process2")).then(null);
                else this.addressesLoaded = true;
            }
        );
    }


    private submitNewAddress(): void {

        let address:Address = <Address>this.addressForm.value;
        console.log(address);

        this.accountService.createNewAddress(address).then(
            () => this.router
                .navigateByUrl("auth/login/" + btoa("process2"))
                .then(null)
            ,
            error => console.log(error)
        )
    }

    private loadPaymentList ():void {

        this.paymentService.findAll().subscribe(
            resp => this.paymentList = resp.body,
            error => console.log(error)
        );
    }

    public selectPaymentMethod(idx:number):void {

        this.order.payment = this.paymentList[idx];
    }

    private loadDeliveryMethodList() :void {

        this.deliveryService.findAll().subscribe(
            resp => this.deliveryMethodList = resp.body,
            error => console.log(error)
        );
    }

    public proceedPayment() :void {

        let api = this.order.payment.api.replace(/\s/g, "");
        this.paymentService.callPaymentApi(api.toLowerCase(), this.order.id);
    }
}
