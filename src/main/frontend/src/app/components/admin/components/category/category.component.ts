import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {CategoryService} from "../../services/category/category.service";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {filter, map} from "rxjs/operators";
import {ICategory} from "../../../../entity/category.model";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
    selector: 'app-category',
    templateUrl: './category.component.html',
    styleUrls: ['./category.component.css'],
    changeDetection: ChangeDetectionStrategy.Default
})
export class CategoryComponent implements OnInit {

    public categories: ICategory[];
    public category:ICategory;
    public addForm:FormGroup;
    public selectedCategory:number;
    public btnDelete:boolean;
    public categoryDeleted:Boolean;

    constructor(protected categoryService: CategoryService, protected formBuilder:FormBuilder) {

        this.categories = [];
        this.btnDelete = true;
        this.categoryDeleted = false;
        this.selectedCategory = -1;
        this.addForm = formBuilder.group({name: ''});
    }

    ngOnInit() {
        this.loadAll();
    }

    loadAll() {
        this.categoryService.findAll().pipe(
            filter((res: HttpResponse<ICategory[]>) => res.ok),
            map((res: HttpResponse<ICategory[]>) => res.body)
        ).subscribe(
            (res: ICategory[]) => {this.categories = res;},
            (res: HttpErrorResponse) => console.log(res.error)
        );
    }

    onSubmit(){
        let catForm:any = this.addForm.value;
        let category:ICategory = {name:catForm.name};
        this.categoryService.create(category).subscribe(
            next => this.loadAll(),
            error => console.log(error),
            () => this.addForm.reset({name:''})
        );
    }

    clickSelect(i){
        this.selectedCategory = i;
        this.btnDelete = false;
    }

    onUpdate(evt){
        let id = evt.target.id;
        if(id != -1){
            let category:ICategory = this.categories[id];
            category.name = evt.target.value;
            this.categories[id] = category;
            this.categoryService.update(category)
                .subscribe(
                next => {return true},
                error => console.log(error)
            );
        }
    }

    onDelete(){
        this.categoryService.delete(this.categories[this.selectedCategory].id)
            .subscribe(
                next => {this.categoryDeleted = next.body; return true;},
                error => console.log(error),
                () => this.afterDelete());
    }

    afterDelete(){
        if(this.categoryDeleted){
            this.categories.splice(this.selectedCategory, 1);
            this.selectedCategory = -1;
            this.categoryDeleted = false;
        }
    }

}
