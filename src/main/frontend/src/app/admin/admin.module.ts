import {NgModule} from "@angular/core";
import {AppComponent} from "../app.component";
import {NavbarComponent} from "../layout/navbar/navbar.component";
import {CategoryComponent} from "./components/category/category.component";
import {AdminComponent} from "./admin.component";
import {BrowserModule} from "@angular/platform-browser";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CategoryService} from "./services/category/category.service";
import {AdminRoutingModule} from "./admin-routing.module";
import {ProductComponent} from "./components/product/product.component";
import {ProductService} from "./services/product/product.service";
import {NumericFloatDirective} from "../directive/numeric-float.directive";

@NgModule({
    declarations: [
        AdminComponent,
        CategoryComponent,
        ProductComponent,
        NumericFloatDirective
    ],
    imports: [
        BrowserModule,
        AdminRoutingModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule
    ],
    providers: [CategoryService, ProductService],
    bootstrap: [AdminComponent]
})
export class AdminModule {
}
