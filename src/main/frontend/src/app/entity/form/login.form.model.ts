export interface ILoginForm {

    login:string;
    password:string;
    remember:boolean
}

export class LoginForm implements ILoginForm {

    constructor(public login:string,
                public password:string,
                public remember:boolean) {

    }
}
