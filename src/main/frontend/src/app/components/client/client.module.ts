import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {ClientComponent} from "./client.component";
import {ClientRoutingModule} from "./client-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {NavigationComponent} from './layout/navigation/navigation.component';
import { HomeComponent } from './layout/home/home.component';
import { ProductComponent } from './layout/product/product.component';


@NgModule({
    declarations: [
        NavigationComponent,
        HomeComponent,
        ProductComponent
    ],
    imports: [
        BrowserModule,
        ClientRoutingModule,
        HttpClientModule
    ],
    providers: [],
    bootstrap: [ClientComponent],
    exports : [NavigationComponent, HomeComponent]
})
export class ClientModule {
}
