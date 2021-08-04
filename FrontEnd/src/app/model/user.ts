import { Checkout } from "../checkout";
import { Role } from "./role";

export class User {
    id!: number;
    username!: string;
    email!: string;
    password!: string;
    phone!: string;
    address!: string;
    firstname!: string;
    lastname!: string;
    roles!: Role;
    deletestatus!: number;
    reset_token!: string;
    orders!: Checkout[]; 
}
