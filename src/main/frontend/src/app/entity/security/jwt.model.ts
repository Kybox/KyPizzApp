export interface IJwtToken {

    token:string;
}

export class JwtToken implements IJwtToken {

    constructor(public token:string) {}
}
