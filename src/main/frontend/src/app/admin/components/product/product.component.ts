import {Component, OnInit} from '@angular/core';
import {IProduct} from "../../../entity/product.model";
import {FormBuilder, FormGroup} from "@angular/forms";
import {ICategory} from "../../../entity/category.model";
import {CategoryService} from "../../services/category/category.service";
import {floatFilter} from "../../../shared/util/numeric-util";

@Component({
    selector: 'app-product',
    templateUrl: './product.component.html',
    styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

    public product:IProduct;
    public addForm:FormGroup;
    public categories:ICategory[];
    public selectedFile:File;
    public imgSrc:any;
    public submit:boolean;
    public priceValue:string;
    public userPrice:string;

    constructor(protected formBuilder:FormBuilder, protected categoryService:CategoryService) {

        this.addForm = formBuilder.group(
            {
                name: '',
                category: '',
                description: '',
                recipe: '',
                available: '',
                price: '',
                tax: ''
            }
        );

        this.submit = true;
        this.priceValue = "0";
        this.userPrice = "0";

        categoryService.findAll().subscribe(
            resp => this.categories = resp.body,
            error => console.log(error)
        );
    }

    ngOnInit() {
    }

    floatFilter(evt:any){
        evt.target.value = floatFilter(evt.target.value);
    }

    onFileSelected(evt) {

        this.selectedFile = evt.target.files[0];

        const reader:FileReader = new FileReader();

        reader.onload = () => this.imgSrc = reader.result;
        reader.readAsDataURL(this.selectedFile);
    }
}
