import {User} from "./account/user.model";
import {Address} from "./account/address.model";
import {OrderProduct} from "./order/order.product.model";
import {Payment} from "./payment/payment.model";
import {DeliveryMethod} from "./order/delivery.method.model";

export interface IOrder {

    id: string;
    restaurant: string;
    customer: User;
    address:Address;
    status: string;
    productList: OrderProduct[];
    payment:Payment;
    paid: boolean;
    deliveryMethod: DeliveryMethod;
    creationDate: Date;
}

export class Order implements IOrder {

    constructor(public id: string,
                public restaurant: string,
                public customer: User,
                public address:Address,
                public status: string,
                public productList: OrderProduct[],
                public payment:Payment,
                public paid: boolean,
                public deliveryMethod: DeliveryMethod,
                public creationDate: Date) {
    }
}
