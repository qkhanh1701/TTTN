import { Product } from "./model/product";

export class Cart {
    id!: number;
    product!: Product;
    quantity!: number;
    price!: number;
    userId!: number;
}
