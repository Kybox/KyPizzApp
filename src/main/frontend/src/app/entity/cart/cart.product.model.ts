export interface ICartProduct {

    id:string;
    name:string;
    quantity:number;
}

export class CartProduct implements ICartProduct {

    constructor(public id:string, public name:string, public quantity:number) {}
}
