export interface IOrderProduct {

    id:string;
    quantity:number;
}

export class OrderProduct implements IOrderProduct {

    constructor(public id:string, public quantity:number) {}
}
