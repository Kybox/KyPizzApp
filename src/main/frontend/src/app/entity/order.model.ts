import {User} from "./account/user.model";

export interface IOrder {

    id: string;
    restaurant: string;
    customer: User;
    status: string;
    productList: {};
    paid: boolean;
    toDeliver: boolean;
    creationDate: Date;
}

export class Order implements IOrder {

    constructor(public id: string,
                public restaurant: string,
                public customer: User,
                public status: string,
                public productList: {},
                public paid: boolean,
                public toDeliver: boolean,
                public creationDate: Date) {
    }
}
