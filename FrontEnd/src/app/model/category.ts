import { Product } from './product';

export class Category {
    id!: number;
    categoryName!: string;
    deletestatus!: number;
    products!: Product[];
}
