export interface ICartKey {

    key:string;
}

export class CartKey implements ICartKey {

    constructor(public key:string) {}
}
