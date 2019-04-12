export interface IPaypalRedirectUrls {

    returnUrl:string;
    cancelUrl:string;
}

export class PaypalRedirectUrls implements IPaypalRedirectUrls {

    constructor(public returnUrl:string, public cancelUrl:string) {}
}
