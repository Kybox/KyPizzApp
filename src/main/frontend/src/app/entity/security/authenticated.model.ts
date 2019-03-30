export interface IAuthenticated {

    authenticated:boolean;
}

export class Authenticated implements IAuthenticated {

    constructor(public authenticated:boolean) {}
}
