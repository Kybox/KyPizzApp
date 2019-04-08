import {Product} from "../product.model";

export interface IProductDetails {

    product:Product;
    quantity:number;
    unitPrice:number;
    tax:number;
    amount:number;
}

export class ProductDetails implements IProductDetails {

    constructor(public product:Product,
                public quantity:number,
                public unitPrice:number,
                public tax:number,
                public amount:number) {}
}
