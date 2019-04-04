export interface IJwtStorageKey {

    key:string;
}

export class JwtStorageKeyModel implements IJwtStorageKey {

    constructor(public key:string) {}
}
