import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {NavigationComponent} from "./components/navigation/navigation.component";
import {AppSettings} from "../../config/app.settings";
import {CartService} from "../../services/cart/cart.service";
import {AuthenticationService} from "../../services/authentication/authentication.service";

@Component({
    selector: 'app-client',
    templateUrl: './client.component.html',
    styleUrls: ['./client.component.css', './components/css/animate.css']
})
export class ClientComponent implements OnInit {

    @ViewChild(NavigationComponent)
    private navigation:NavigationComponent;

    public authenticated:boolean;

    constructor(private router: Router,
                private cartService:CartService,
                private authService:AuthenticationService) {

        this.authenticated = false;
    }

    ngOnInit() {

        this.getCartKey();
        //this.navigation.isAuthenticated(null);
    }

    componentAdded(evt):void {

        if(typeof evt.notify !== "undefined"){
            evt.notify.subscribe(
                resp => this.navigation.isAuthenticated(resp)
            )
        }
    }

    getCartKey() :void {

        this.cartService.getKey().subscribe(
            resp => AppSettings.COOKIE_CART_KEY = resp.body.key,
            error => console.log(error),
            () => this.getJwtStorageKey()
        );
    }

    getJwtStorageKey() : void {

        this.authService.getStorageKey().subscribe(
            resp => AppSettings.JWT_STORAGE_KEY = resp.body.key,
            error => console.log(error),
            () => this.checkAuthentication()
        );
    }

    checkAuthentication() :void {

        let token:string = localStorage.getItem(AppSettings.JWT_STORAGE_KEY);
        if(token != null) this.navigation.isAuthenticated(token);
        else{
            localStorage.removeItem(AppSettings.JWT_STORAGE_KEY);
            token = sessionStorage.getItem(AppSettings.JWT_STORAGE_KEY);
            if(token != null) this.navigation.isAuthenticated(token);
            else sessionStorage.removeItem(AppSettings.JWT_STORAGE_KEY);
        }
    }
}
