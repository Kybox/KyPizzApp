import {IAuthority} from "../authority.model";
import {Address} from "./address.model";

export interface IUser {

    id?:string;
    nickName?:string;
    firstName?:string;
    lastName?:string;
    email?:string
    password?:string;
    activated?:boolean;
    address?:Address;
    imgUrl?:string;
    creationDate?:Date;
    authorities?:IAuthority[];
}

export class User implements IUser {

    constructor(init?:Partial<User>){
        Object.assign(this, init);
    }
}
