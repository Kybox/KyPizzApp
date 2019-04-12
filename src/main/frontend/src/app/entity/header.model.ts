export interface IHeader {
    
    key:string;
    value:string;
}

export class Header implements IHeader {
    
    constructor(public key:string, public value:string) {}
}
