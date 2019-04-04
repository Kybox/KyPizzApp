import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {CategoryService} from "../../../../services/category/category.service";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {filter, map} from "rxjs/operators";
import {Category, ICategory} from "../../../../entity/category.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
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
    public btnUpdateDisabled:boolean;

    public categoryDeleted: Boolean;

    public alertForAdd: boolean;
    public alertForUpdate: boolean;
    public alertError: boolean;
    public alertSuccess: boolean;
    public alertInfo: string;

    private productList: IProduct[];

    constructor(private categoryService: CategoryService,
                private productService: ProductService,
                private formBuilder: FormBuilder) {

        this.categories = [];

        this.btnDelete = true;
        this.btnUpdateDisabled = true;

        this.categoryDeleted = false;
        this.selectedCategory = -1;

        this.alertForAdd = false;
        this.alertForUpdate = false;
        this.alertError = false;
        this.alertSuccess = false;


        this.addForm = formBuilder.group(
            {
                name: ['', Validators.required], description: ['', Validators.required]
            });
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
        this.alertForAdd = false;
        this.alertForUpdate = false;
    }

    addNew(): void {

        let catForm: any = this.addForm.value;
        let category: Category = {id: null, name: catForm.name, description: catForm.description};

        this.categoryService.create(category).subscribe(
            () => {
                this.alertForAdd = true;
                this.alertSuccess = true;
                this.alertInfo = "New category added.";
                setTimeout(() => {
                    this.hideAlerts()
                }, 3000);
                this.loadAll();
            },
            error => {
                console.log(error);
                this.alertForAdd = true;
                this.alertError = true;
                this.alertInfo = error;
            },
            () => this.addForm.reset({name: '', description: ''})
        );
    }

    clickSelect(i): void {

        this.selectedCategory = i;
        this.btnDelete = false;

        this.hideAlerts();
    }

    onUpdate(id: number, name: string, desc: string): void {

        if (id != -1) {
            let category: Category = this.categories[id];
            category.name = name;
            category.description = desc;

            this.categories[id] = category;

            this.categoryService
                .update(category)
                .subscribe(
                    () => {
                        this.alertForUpdate = true;
                        this.alertSuccess = true;
                        this.alertInfo = "Category has been updated."
                    },
                    resp => {
                        console.log(resp);
                        this.alertForUpdate = true;
                        this.alertError = true;
                        this.alertInfo = resp.error.message;
                    }
                );
        }
    }

    onLiveUpdate(input:any){

        console.log(input.name);
        if(input.name === "updateDesc"){
            this.btnUpdateDisabled = input.value === '';
        }
        //this.btnUpdate = input === '';

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
                    this.alertInfo = "The category " + this.categories[this.selectedCategory].name + " has been removed.";
                    this.alertSuccess = true;
                    this.alertForUpdate = true;
                    setTimeout(() => {
                        this.hideAlerts();
                    }, 3000);
                },
                error => {
                    console.log(error);
                    this.alertForUpdate = true;
                    this.alertError = true;
                    this.alertInfo = error;
                },
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
