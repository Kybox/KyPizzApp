export interface IDeliveryMethod {
    
    id:string;
    description:string;
    amount:number;
}

export class DeliveryMethod implements IDeliveryMethod {
    
    constructor(public id:string, public description:string, public amount:number) {}
}
