import {Component, OnInit} from '@angular/core';
import {CategoryService} from "../../../../services/category/category.service";
import {ICategory} from "../../../../entity/category.model";
import {Router} from "@angular/router";

@Component({
    selector: 'ky-nav',
    templateUrl: './navigation.component.html',
    styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

    public categoryList:ICategory[];

    constructor(private categoryService:CategoryService, private router:Router) {
    }

    ngOnInit() {

        this.loadCategories();
    }

    loadCategories(){

        this.categoryService.findAll().subscribe(
            resp => this.categoryList = resp.body,
            error => console.log(error)
        );
    }

    goTo(path:string){
        this.router.navigateByUrl(btoa(path));
    }

}
