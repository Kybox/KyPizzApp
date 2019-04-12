import {Header} from "../../header.model";

export interface IPaypalPayUrl {

    payUrl:string;
    headers:Header[];
}

export class PaypalPayUrl implements IPaypalPayUrl {

    constructor(public payUrl:string, public headers:Header[]) {}
}
