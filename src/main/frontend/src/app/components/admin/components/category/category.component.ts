import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {CategoryService} from "../../../../services/category/category.service";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {filter, map} from "rxjs/operators";
import {ICategory} from "../../../../entity/category.model";
import {FormBuilder, FormGroup} from "@angular/forms";
import {IProduct} from "../../../../entity/product.model";
import {ProductService} from "../../../../services/product/product.service";

@Component({
    selector: 'app-category',
    templateUrl: './category.component.html',
    styleUrls: ['./category.component.css'],
    changeDetection: ChangeDetectionStrategy.Default
})
export class CategoryComponent implements OnInit {

    public categories: ICategory[];
    public category: ICategory;
    public addForm: FormGroup;
    public selectedCategory: number;
    public btnDelete: boolean;
    public categoryDeleted: Boolean;
    public alertError: boolean;
    public alertSuccess: boolean;
    public alertInfo: string;

    private productList: IProduct[];

    constructor(private categoryService: CategoryService,
                private productService: ProductService,
                private formBuilder: FormBuilder) {

        this.categories = [];
        this.btnDelete = true;
        this.categoryDeleted = false;
        this.selectedCategory = -1;
        this.alertError = false;
        this.alertSuccess = false;
        this.addForm = formBuilder.group({name: ''});
    }

    ngOnInit() {

        this.loadAll();
    }

    loadAll(): void {
        this.categoryService.findAll().pipe(
            filter((res: HttpResponse<ICategory[]>) => res.ok),
            map((res: HttpResponse<ICategory[]>) => res.body)
        ).subscribe(
            (res: ICategory[]) => {
                this.categories = res;
            },
            (res: HttpErrorResponse) => console.log(res.error)
        );
    }

    hideAlerts(): void {
        this.alertSuccess = false;
        this.alertError = false;
    }

    onSubmit(): void {
        let catForm: any = this.addForm.value;
        let category: ICategory = {name: catForm.name};
        this.categoryService.create(category).subscribe(
            () => this.loadAll(),
            error => console.log(error),
            () => this.addForm.reset({name: ''})
        );
    }

    clickSelect(i): void {
        this.selectedCategory = i;
        this.btnDelete = false;
        this.alertError = false;
        this.alertSuccess = false;
    }

    onUpdate(evt): void {
        let id = evt.target.id;
        if (id != -1) {
            let category: ICategory = this.categories[id];
            category.name = evt.target.value;
            this.categories[id] = category;
            this.categoryService.update(category)
                .subscribe(
                    () => {
                        return true
                    },
                    error => console.log(error)
                );
        }
    }

    onDelete(): void {

        this.productService
            .findByCategory(this.categories[this.selectedCategory])
            .subscribe(
                resp => this.productList = resp.body,
                error => console.log(error),
                () => {
                    if (this.productList.length > 0) {
                        this.alertInfo = "Vous ne pouvez pas supprimer cette catégorie car il existe " + this.productList.length + " produits qui y sont associés !";
                        this.alertError = true;
                    } else this.deleteCategory();
                }
            );
    }

    deleteCategory(): void {
        this.categoryService.delete(this.categories[this.selectedCategory].id)
            .subscribe(
                next => {
                    this.categoryDeleted = next.body;
                    this.alertInfo = "La catégorie " + this.categories[this.selectedCategory].name + " viens d'être supprimée.";
                    this.alertSuccess = true;
                    return true;
                },
                error => console.log(error),
                () => this.afterDelete());
    }

    afterDelete(): void {
        if (this.categoryDeleted) {
            this.categories.splice(this.selectedCategory, 1);
            this.selectedCategory = -1;
            this.categoryDeleted = false;
        }
    }
}
