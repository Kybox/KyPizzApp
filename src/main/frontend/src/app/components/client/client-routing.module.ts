import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ClientComponent} from "./client.component";
import {ProductComponent} from "./components/product/product.component";
import {AuthenticationComponent} from "./components/authentication/authentication.component";
import {AccountComponent} from "./components/account/account.component";

const routes: Routes = [
    {
        path: "home",
        redirectTo: "",
        pathMatch: "prefix"
    },
    {
        path: "",
        component: ClientComponent,
        children: [
            {path: ":id", component: ProductComponent},
            {path: "auth/login", component: AuthenticationComponent},
            {path: "auth/account", component: AccountComponent}
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ClientRoutingModule {
}
