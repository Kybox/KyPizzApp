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
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AccountComponent} from './components/account/account.component';
import {CookieService} from "ngx-cookie-service";
import { OrderComponent } from './components/order/order.component';
import {NumericIntegerFilter} from "../../directive/numeric-integer.directive";

@NgModule({
    declarations: [
        NavigationComponent,
        HomeComponent,
        ProductComponent,
        AuthenticationComponent,
        FooterComponent,
        AccountComponent,
        OrderComponent,
        NumericIntegerFilter
    ],
    imports: [
        BrowserModule,
        ClientRoutingModule,
        HttpClientModule,
        ReactiveFormsModule,
        FormsModule
    ],
    providers: [CookieService],
    bootstrap: [ClientComponent],
    exports: [NavigationComponent, HomeComponent, FooterComponent]
})
export class ClientModule {
}
