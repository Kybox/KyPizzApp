import {Component, OnInit} from '@angular/core';
import {IProduct} from "../../../../entity/product.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ICategory} from "../../../../entity/category.model";
import {CategoryService} from "../../../../services/category/category.service";
import 'bootstrap4-toggle/js/bootstrap4-toggle';
import {ProductService} from "../../../../services/product/product.service";
import {HttpClient, HttpErrorResponse, HttpEventType, HttpResponse} from "@angular/common/http";
import {filter, map} from "rxjs/operators";
import {Observable} from "rxjs";
import {FileService} from "../../../../services/file/file.service";

@Component({
    selector: 'app-product',
    templateUrl: './product.component.html',
    styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

    public title: string;
    public product: IProduct;
    public addForm: FormGroup;
    public categories: ICategory[];
    public productList: IProduct[];
    public selectedFile: File;
    public selectedCategory:number;


    public imgSrc: any;
    public submitted: boolean;
    public priceValue: string;
    public userPrice: string;
    public availableLabel: string;
    public progress: string;

    private editProduct:boolean;
    private selectedProduct: IProduct;
    private imgPath: string;

    constructor(protected formBuilder: FormBuilder,
                protected categoryService: CategoryService,
                protected productService: ProductService,
                private fileService:FileService,
                protected http:HttpClient) {

        this.title = "Ajouter un nouveau produit";
        this.submitted = false;
        this.priceValue = "0";
        this.userPrice = "0";
        this.progress = "";
        this.editProduct = false;

        this.availableLabel = "Produit disponible";

        this.selectedCategory = -1;
        categoryService.findAll().subscribe(
            resp => this.categories = resp.body,
            error => console.log(error)
        );
    }

    ngOnInit() {
        this.buildForm();
        //this.loadAll();
    }

    buildForm() {
        this.addForm = this.formBuilder.group({
            name: [null, Validators.required],
            category: ['', Validators.required],
            description: [null, Validators.required],
            recipe: [null],
            available: [false],
            price: [null, Validators.required],
            tax: [null, Validators.required],
            img: [null, Validators.required]
        });
    }

    loadAll() {
        this.productService
            .findAll()
            .pipe(
                filter((response: HttpResponse<IProduct[]>) => response.ok),
                map((response: HttpResponse<IProduct[]>) => response.body)
            )
            .subscribe(
                (response: IProduct[]) => {
                    this.productList = response;
                },
                (response: HttpErrorResponse) => console.log(response)
            );
    }

    onCategorySelected(evt){

        let categoryIndex:number = evt.target.value;
        this.loadProductListByCategory(this.categories[categoryIndex]);
    }

    loadProductListByCategory(category:ICategory) {

        this.productService
            .findByCategory(category)
            .subscribe(
                resp => this.productList = resp.body,
                error => console.log(error)
            );
    }

    onProductSelected(evt) {

        let productIndex: number = evt.target.value;
        this.selectedProduct = this.productList[productIndex];
        this.title = this.selectedProduct.category.name + " > " + this.selectedProduct.name;
        this.populateForm(this.selectedProduct);
        this.editProduct = true;
    }

    populateForm(obj: IProduct) {

        for (let key in this.addForm.controls) {
            if (key === "category") {
                this.addForm.controls[key].setValue(this.getCategoryIndex(obj[key].id));
                continue;
            }
            if(key === "img") {
                this.fileService
                    .downloadFile(btoa(obj[key]))
                    .subscribe(resp => {

                        let reader:FileReader = new FileReader();
                        reader
                            .addEventListener(
                                "load",
                                () =>{this.imgSrc = reader.result;},
                                false);
                        reader.readAsDataURL(resp);
                    }
                );
                document.getElementById("imgLabel").innerText = obj[key];
                continue;
            }
            this.addForm.controls[key].setValue(obj[key]);
        }
    }

    getCategoryIndex(id: string): any {

        for (let index in this.categories) {
            let cat: ICategory = this.categories[index];
            if (cat.id === id) return index;
        }
    }

    onAvailableChange(evt) {
        if (evt.target.checked) this.availableLabel = "Produit disponible";
        else this.availableLabel = "Produit indisponible";
    }

    onFileSelected(evt) {

        this.selectedFile = evt.target.files[0];

        const reader: FileReader = new FileReader();
        reader.onload = () => this.imgSrc = reader.result;
        reader.readAsDataURL(this.selectedFile);

        document.getElementById("imgLabel").innerText = this.selectedFile.name;

        const formData: FormData = new FormData();
        formData.append("file", this.selectedFile);

        this.productService.uploadFile(formData).subscribe(
            event => {
                if (event.type === HttpEventType.UploadProgress)
                    this.progress = Math.round(100 * event.loaded / event.total) + " %";
                else if (event instanceof HttpResponse) {
                    this.progress = "100 %";
                    this.imgPath = event.body['file'];
                }
            },
            error => console.log(error),
            () => {
                this.progress = "";
                if(this.editProduct) {
                    this.selectedProduct.img = this.imgPath;
                    this.updateProduct();
                }
            }
        );
    }

    onSubmit() {

        let product = <IProduct>this.addForm.value;
        product.img = this.imgPath;
        product.creationDate = new Date();
        product.category = this.categories[this.addForm.controls['category'].value];

        console.log(this.addForm.controls['category']);
        console.log(product);

        this.productService.create(product).subscribe(
            response => {
                console.log(response);
                this.addForm.reset();
            },
            error => console.log(error)
        );
    }

    onUpdate(evt){

        if(this.editProduct){
            let key:string = evt.target.name;

            if(key === "available") this.selectedProduct[key] = evt.target.checked;
            else this.selectedProduct[key] = evt.target.value;

            this.updateProduct();

            if(key === "name")
                this.title = this.selectedProduct.category.name + " > " + this.selectedProduct.name;
        }
    }

    updateProduct():void {

        this.productService
            .update(this.selectedProduct)
            .subscribe(
                () => {return true;},
                error => console.log(error)
            );
    }

    refresh(): void {
        window.location.reload();
    }
}
