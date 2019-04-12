import {NgModule} from "@angular/core";
import {CategoryComponent} from "./components/category/category.component";
import {AdminComponent} from "./admin.component";
import {BrowserModule} from "@angular/platform-browser";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AdminRoutingModule} from "./admin-routing.module";
import {ProductComponent} from "./components/product/product.component";
import {NumericFloatDirective} from "../../directive/numeric-float.directive";
import { UserComponent } from './components/user/user.component';
import { PaymentComponent } from './components/payment/payment.component';
import { DeliveryComponent } from './components/delivery/delivery.component';

@NgModule({
    declarations: [
        AdminComponent,
        CategoryComponent,
        ProductComponent,
        NumericFloatDirective,
        UserComponent,
        PaymentComponent,
        DeliveryComponent
    ],
    imports: [
        BrowserModule,
        AdminRoutingModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule
    ],
    providers: [],
    bootstrap: [AdminComponent]
})
export class AdminModule {
}
