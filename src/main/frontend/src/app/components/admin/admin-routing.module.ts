import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {CategoryComponent} from "./components/category/category.component";
import {ProductComponent} from "./components/product/product.component";
import {AdminComponent} from "./admin.component";
import {UserComponent} from "./components/user/user.component";

const routes: Routes = [
    {
        path: "restricted/admin",
        component:AdminComponent,
        children:[
            {path: "user", component: UserComponent},
            {path: "category", component: CategoryComponent},
            {path: "product", component: ProductComponent}
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AdminRoutingModule { }
