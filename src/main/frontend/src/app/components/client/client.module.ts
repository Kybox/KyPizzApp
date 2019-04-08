import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {ClientComponent} from "./client.component";
import {ClientRoutingModule} from "./client-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {NavigationComponent} from './components/navigation/navigation.component';
import {HomeComponent} from './components/home/home.component';
import {ProductComponent} from './components/product/product.component';
import {AuthenticationComponent} from './components/authentication/authentication.component';
import {FooterComponent} from './components/footer/footer.component';
import {ReactiveFormsModule} from "@angular/forms";
import {AccountComponent} from './components/account/account.component';
import {CookieService} from "ngx-cookie-service";
import { OrderComponent } from './components/order/order.component';

@NgModule({
    declarations: [
        NavigationComponent,
        HomeComponent,
        ProductComponent,
        AuthenticationComponent,
        FooterComponent,
        AccountComponent,
        OrderComponent
    ],
    imports: [
        BrowserModule,
        ClientRoutingModule,
        HttpClientModule,
        ReactiveFormsModule
    ],
    providers: [CookieService],
    bootstrap: [ClientComponent],
    exports: [NavigationComponent, HomeComponent, FooterComponent]
})
export class ClientModule {
}
