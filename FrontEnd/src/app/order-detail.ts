import { Checkout } from "./checkout";
import { Product } from "./model/product";

export class OrderDetail {
    order!: Checkout;
    product!: Product;
    quantity!: number;
    amount!: number;
    discount!: number;
}
