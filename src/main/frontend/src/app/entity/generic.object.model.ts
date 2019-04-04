export interface IGenericObject {

    data:string;
}

export class GenericObject implements IGenericObject {

    constructor(public data:string) {}
}
