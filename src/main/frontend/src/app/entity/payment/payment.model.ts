export interface IPayment {

    id:string;
    api:string;
    activated:boolean;
}

export class Payment implements IPayment {

    constructor(public id:string, public api:string, public activated:boolean) {}
}
