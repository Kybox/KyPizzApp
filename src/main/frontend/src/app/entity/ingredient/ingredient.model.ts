export interface IIngredient {

    id?:string;
    name?:string;
    description?:string;
    unit?:string;
    quatity?:number;
}

export class Ingredient implements IIngredient {

    constructor(public id?:string,
                public name?:string,
                public description?:string,
                public unit?:string,
                public quantity?:number) {

    }
}
