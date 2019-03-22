import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {CategoryComponent} from "./components/category/category.component";
import {ProductComponent} from "./components/product/product.component";

const routes: Routes = [
    {path: "admin/category", component: CategoryComponent},
    {path: "admin/product", component: ProductComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AdminRoutingModule { }
