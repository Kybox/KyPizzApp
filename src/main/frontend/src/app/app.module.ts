import {BrowserModule} from '@angular/platform-browser';
import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {FormsModule} from "@angular/forms";
import {ReactiveFormsModule} from "@angular/forms";
import {AdminModule} from "./components/admin/admin.module";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ClientComponent } from './components/client/client.component';
import {ClientModule} from "./components/client/client.module";
import {TokenInterceptor} from "./shared/interceptor/token.interceptor";

@NgModule({
    declarations: [
        AppComponent,
        ClientComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        AdminModule,
        ClientModule,
        BrowserAnimationsModule
    ],
    providers: [{
        provide: HTTP_INTERCEPTORS,
        useClass: TokenInterceptor,
        multi: true
    }],
    bootstrap: [AppComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule {
}
