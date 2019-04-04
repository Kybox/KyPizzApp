export interface IJwtObject {

    token:string;
}

export class JwtObject implements IJwtObject {

    constructor(public token:string) {}
}
