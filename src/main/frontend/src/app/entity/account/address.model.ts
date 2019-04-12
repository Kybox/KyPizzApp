export interface IAddress {

    id:string;
    firstName:string;
    lastName:string;
    streetNumber:string;
    streetName:string;
    zipCode:string;
    city:string;
    country:string;
    additionalInfo?:string;
}

export class Address implements IAddress {

    constructor(public id:string,
                public firstName:string,
                public lastName:string,
                public streetNumber:string,
                public streetName:string,
                public zipCode:string,
                public city:string,
                public country:string,
                public additionalInfo?:string) {}
}
