import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ClientComponent} from "./client.component";
import {ProductComponent} from "./layout/product/product.component";

const routes: Routes = [
    {
        path: "",
        component: ClientComponent,
        children: [
            {path: ":id", component: ProductComponent}
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ClientRoutingModule {
}
