import {ICartProduct} from "./cart.product.model";

export interface ICart {

    productList:ICartProduct[];
}

export class Cart implements ICart {

    constructor(public productList:ICartProduct[]) {}
}
