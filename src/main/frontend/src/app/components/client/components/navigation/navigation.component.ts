import {Component, OnInit} from '@angular/core';
import {CategoryService} from "../../../../services/category/category.service";
import {ICategory} from "../../../../entity/category.model";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../../../services/authentication/authentication.service";
import {Authenticated} from "../../../../entity/security/authenticated.model";
import {AppSettings} from "../../../../config/app.settings";

@Component({
    selector: 'ky-nav',
    templateUrl: './navigation.component.html',
    styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

    public categoryList: ICategory[];
    public authenticated: boolean;

    constructor(private categoryService: CategoryService,
                private authService: AuthenticationService,
                private router: Router) {

        this.authenticated = false;
    }

    ngOnInit() {

        this.loadCategories();
    }

    loadCategories(): void {

        this.categoryService.findAll().subscribe(
            resp => this.categoryList = resp.body,
            error => console.log(error)
        );
    }

    isAuthenticated(token: string): void {

        if (token != null) {
            this.authService.isAuthenticated(token).subscribe(
                resp => {
                    let auth: Authenticated = resp.body;
                    this.authenticated = auth.authenticated;

                    if(!this.authenticated) AuthenticationService.clearToken();
                },
                error => console.log(error)
            );
        }

        console.log("NAV auth : " + this.authenticated);
    }

    goTo(path: string): void {
        this.router.navigateByUrl(btoa(path)).then(null);
    }

    getRoute(category: string): string {
        return "/" + btoa(category);
    }
}
