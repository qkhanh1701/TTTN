import { Component, OnInit } from '@angular/core';
import { Checkout } from 'src/app/checkout';
import { CartService } from 'src/app/service/cart.service';
import { TokenStorageService } from 'src/app/service/token-storage.service';
import { Router } from '@angular/router';
import { UserService } from 'src/app/service/user.service';
import { OrderDetail } from 'src/app/order-detail';
import { Product } from 'src/app/model/product';

@Component({
  selector: 'app-orderlist',
  templateUrl: './orderlist.component.html',
  styleUrls: ['./orderlist.component.css']
})

export class OrderlistComponent implements OnInit {
  [x: string]: any;

  products: Array<Checkout> = [];
  detailsOrder: Array<OrderDetail> = [];
  token: any;
  order: any;
  status!: String;
  namePage: String = 'orderDetail/';

  constructor(private cartService: CartService, private tokenStorageService: TokenStorageService,  private router : Router, private userService: UserService) { }

  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();
    this.getOrderListByUserId(user.id);
  }

  getOrderListByUserId(userId: number){
    this.token = this.tokenStorageService.getToken();
    this.cartService.getOrderList(this.token, userId)
          .subscribe(
            (data: Checkout[]) => {
              this.products = data;
              console.log(this.products);
            },
            error => {
              console.log(error);
            });
    }

  ridrect(id: number)
  {
    this.router.navigate([`${this.namePage}${id}`]);
  }

  cancel(id: number)
  {
    this.token = this.tokenStorageService.getToken();
    this.userService.cancel(this.token,id,3)
    .subscribe(
      (data: Checkout) => {
        this.order = data;
        this.getOrderListByUserId(this.order.user_id);
        this.userService.getOrderDetails(this.token, id)
          .subscribe(
            (data: OrderDetail[]) => {
              this.detailsOrder = data;
              console.log("detail2")
              for(let detail of this.detailsOrder){
                let quantity: number;
                let quantityProduct: number;
                let idProduct: number;
                let quantityProductAfterCancel: number;
                quantity = detail.quantity;
                quantityProduct = detail.product.quantity;
                idProduct = detail.product.id;
                quantityProductAfterCancel = quantityProduct + quantity;
                console.log(quantityProductAfterCancel);
                this.userService.updateQuantityProduct(idProduct, quantityProductAfterCancel)
                .subscribe(
                  (data: Product) => {
                    console.log("detail3")
                  },
                  error => {
                    console.log(error);
                  });
                }
            },
            error => {
              console.log(error);
            });
        console.log("detail1");
      },
      error => {
        console.log(error);
      });
  }

    reloadPage(): void {
      window.location.reload();
    }
  }
  
