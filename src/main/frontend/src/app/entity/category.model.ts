export interface ICategory {

  id?:string;
  name?:string;
}

export class Category implements ICategory {

  constructor(public id?:string, public name?:string) {}
}
