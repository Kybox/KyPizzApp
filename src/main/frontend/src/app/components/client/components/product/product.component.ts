import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {CategoryService} from "../../../../services/category/category.service";
import {Category, ICategory} from "../../../../entity/category.model";
import {IProduct} from "../../../../entity/product.model";
import {ProductService} from "../../../../services/product/product.service";
import {FileService} from "../../../../services/file/file.service";
import {CartProduct, ICartProduct} from "../../../../entity/cart/cart.product.model";
import {CookieService} from "ngx-cookie-service";
import {AppSettings} from "../../../../config/app.settings";
import {Cart} from "../../../../entity/cart/cart.model";
import {CartService} from "../../../../services/cart/cart.service";

@Component({
    selector: 'app-product',
    templateUrl: './product.component.html',
    styleUrls: ['./product.component.css', '../../client.component.css']
})
export class ProductComponent implements OnInit {

    @Output()
    public cartNotifier:EventEmitter<ICartProduct>;

    public productList: IProduct[];
    public category:Category;
    public backgroundList = [];

    constructor(private route: ActivatedRoute,
                private categoryService: CategoryService,
                private productService: ProductService,
                private fileService:FileService,
                private cookieService:CookieService,
                private cartService:CartService) {

        this.cartNotifier = new EventEmitter();

        let id = atob(this.route.snapshot.params.id);

        categoryService.findById(id).subscribe(
            resp => this.category = resp.body,
            error => console.log(error),
            () => this.getProductList(this.category)
        );
    }

    ngOnInit() {

    }

    getProductList(category: ICategory): void {

        this.productService.findByCategory(category).subscribe(
            resp => this.productList = resp.body,
            error => console.log(error),
            () => {
                for(let i:number = 0; i < this.productList.length; i++)
                    this.getBackgroundList(i);
            }
        );
    }

    getBackgroundList(productIdx:number) {

        this.fileService
            .downloadFile(this.productList[productIdx].img)
            .subscribe(
                resp => {
                    let reader:FileReader = new FileReader();
                    reader
                        .addEventListener(
                            "load",
                            () => this.backgroundList[productIdx] = reader.result.toString(),
                            false);
                    reader.readAsDataURL(resp);
                });
    }

    addToCart(id:string, name:string, quantity:string) {

        this.cartService
            .addProduct(new CartProduct(id, name, Number(quantity)))
            .subscribe(
                () => this.cartService.getCart(),
                error => console.log(error)
            );
    }
}
