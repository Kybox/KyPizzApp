import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from "@angular/common/http";
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NavbarComponent} from './layout/navbar/navbar.component';
import {FormsModule} from "@angular/forms";
import {ReactiveFormsModule} from "@angular/forms";
import {AdminModule} from "./admin/admin.module";

@NgModule({
    declarations: [
        AppComponent,
        NavbarComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        AdminModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
