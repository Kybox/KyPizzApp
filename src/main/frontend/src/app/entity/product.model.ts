import {ICategory} from "./category.model";

export interface IProduct {

    id?:string;
    name?:string;
    category?:ICategory;
    description?:string;
    recipe?:string
    available?:boolean;
    price?:number;
    tax?:number;
    img?:string;
    creationDate?:Date;
}

export class Product implements IProduct {

    constructor(public id?:string,
                public name?:string,
                public category?:ICategory,
                public description?:string,
                public recipe?:string,
                public available?:boolean,
                public price?:number,
                public tax?:number,
                public img?:string,
                public creationDate?:Date) {

    }
}
