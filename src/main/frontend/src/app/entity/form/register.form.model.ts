export interface IRegisterForm {

    nickName:string;
    firstName:string;
    lastName:string;
    email:string;
    password1:string;
    password2:string;
}

export class RegisterForm implements IRegisterForm {

    constructor(public nickName:string,
                public firstName:string,
                public lastName:string,
                public email:string,
                public password1:string,
                public password2:string) {
    }
}
