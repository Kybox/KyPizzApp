import {IAuthority} from "./authority.model";

export interface IUser {

    id?:string;
    nickName?:string;
    firstName?:string;
    lastName?:string;
    email?:string
    password?:string;
    activated?:boolean;
    imgUrl?:string;
    creationDate?:Date;
    authorities?:IAuthority[];
}

export class User implements IUser {

    constructor(init?:Partial<User>){
        Object.assign(this, init);
    }
}
