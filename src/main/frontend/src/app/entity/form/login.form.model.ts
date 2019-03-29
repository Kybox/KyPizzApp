export interface ILoginForm {

    login:string;
    password:string;
}

export class LoginForm implements ILoginForm {

    constructor(public login:string,
                public password:string) {

    }
}
