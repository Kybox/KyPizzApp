import {Component, OnInit} from '@angular/core';
import {CategoryService} from "../../../../services/category/category.service";
import {ICategory} from "../../../../entity/category.model";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../../../services/authentication/authentication.service";
import {Authenticated} from "../../../../entity/security/authenticated.model";
import {CartService} from "../../../../services/cart/cart.service";
import {Cart} from "../../../../entity/cart/cart.model";

@Component({
    selector: 'ky-nav',
    templateUrl: './navigation.component.html',
    styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

    public categoryList: ICategory[];
    public authenticated: boolean;
    public cartTotalItems: number;
    public cart: Cart;

    constructor(private categoryService: CategoryService,
                private authService: AuthenticationService,
                private router: Router, private cartService: CartService) {

        this.cartTotalItems = 0;
        this.authenticated = false;

        this.router.routeReuseStrategy.shouldReuseRoute = function () {
            return false;
        }
    }

    ngOnInit() {

        this.loadCategories();
        this.listenCartUpdate();
        this.cartService.getCart();
    }

    loadCategories(): void {

        this.categoryService.findAll().subscribe(
            resp => this.categoryList = resp.body,
            error => console.log(error)
        );
    }

    listenCartUpdate(): void {

        this.cartService.cartSubject.subscribe(
            resp => {
                this.cart = resp;
                if(this.cart !== undefined)
                    this.cartTotalItems = this.getTotalItems();
            }
        );
    }

    getTotalItems(): number {

        let total: number = 0;

        if(this.cart.productList !== undefined){
            for (let product of this.cart.productList)
                total += product.quantity;
        }

        return total;
    }

    clickOrder():void {

        this.authService.isAuthorized().subscribe(
            resp => {
                if(resp.body.authenticated)
                    this.router.navigateByUrl("process/order/" + btoa("process1")).then(null);
                else this.router.navigateByUrl("auth/login/" + btoa("cart")).then(null);
            },
            error => console.log(error)
        );
    }

    isAuthenticated(token: string): void {

        if (token != null) {
            this.authService.isAuthenticated(token).subscribe(
                resp => {
                    let auth: Authenticated = resp.body;
                    this.authenticated = auth.authenticated;

                    if (!this.authenticated) AuthenticationService.clearToken();
                },
                error => console.log(error)
            );
        }
    }

    goTo(path: string): void {
        this.router.navigateByUrl(btoa(path)).then(null);
    }

    getRoute(category: string): string {
        return "/" + btoa(category);
    }
}
