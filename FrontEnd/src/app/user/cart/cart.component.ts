import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Cart } from 'src/app/cart';
import { Product } from 'src/app/model/product';
import { CartService } from 'src/app/service/cart.service';
import { CountService } from 'src/app/service/count.service';
import { TokenStorageService } from 'src/app/service/token-storage.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  carts: Array<Cart> = [];
  token: any;
  indexStr!: String;
  count!: number;
  totalCart: number = 0;
  totalDiscount: number = 0;
  totalAfterDis: number = 0;
  able: boolean = true;

  constructor(private router : Router, private cartService: CartService, private userService: UserService, private tokenStorageService: TokenStorageService, private countService: CountService) { }

  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();
    console.log(user.id);
    this.getCartDetailByUser(user.id);
  }

  getCartDetailByUser(userId: number){
    this.token = this.tokenStorageService.getToken();
    console.log(this.token);
    this.cartService.getCartDetailByUser(this.token,userId)
          .subscribe(
            (data: Cart[]) => {
              console.log(data);
              this.carts = data; 
              this.countCartById();
              this.total();
              this.totalDis();
              this.totalAfterDis = this.totalCart - this.totalDiscount;
            },
            error => {
              console.log(error);
            });
  }

  countCartById(){
    this.token = this.tokenStorageService.getToken();
    const user = this.tokenStorageService.getUser();
    this.cartService.countCartById(this.token, user.id)
          .subscribe(
            (data) => {
              this.count = data;
              this.countService.changeCount(this.count);
              if(this.count == 0)
              {
                this.able = false;
              }
            },
            error => {
              console.log(error);
            }
          );
  }

  deleteCartById(id: number){
    this.token = this.tokenStorageService.getToken();
    const user = this.tokenStorageService.getUser();
    let cart: Cart;
    let quantity: number;
    let quantityCart: number;

    this.cartService.getCartByIdCart(this.token, id)
        .subscribe(
          (data: Cart) => {
            cart = data; 
            console.log(cart);
            quantity = cart.product.quantity;
            console.log(quantity);
            quantityCart = cart.quantity;
            let quantityPro: number;
            quantityPro = cart.product.quantity + cart.quantity;
            this.userService.updateQuantityProduct(cart.product.id,quantityPro)
                .subscribe(
                  (data: Product) => {
                    this.cartService.deleteCartById(this.token, id)
                        .subscribe(
                          (data) => {
                            this.carts = data;
                            this.cartService.countCartById(this.token, user.id)
                                  .subscribe(
                                    (data) => {
                                      this.count = data;
                                      this.countService.changeCount(this.count);
                                      this.total();
                                      this.totalDis();
                                      this.totalAfterDis = this.totalCart - this.totalDiscount;
                                    },
                                    error => {
                                      console.log(error);
                                    }
                                  );
                          },
                          error => {
                            console.log(error);
                          }
                        );
                  },
                  error => {
                  
                  }
                )
          },
          error => {
                            
          }
        )
  }

  increase(id: number, index: number){
    this.token = this.tokenStorageService.getToken();
    let cart: Cart;
    let quantity: number;
    this.cartService.getCartByIdCart(this.token, id)
    .subscribe(
      (data: Cart) => {
        cart = data; 
        quantity = cart.product.quantity;
        if(quantity == 0)
        {
          window.confirm("Sorry, no more product in stock!!!");
        }else{
          index++;
          this.cartService.updateCartById(this.token, id, index)
          .subscribe(
            (data: Cart[]) => {
              this.carts = data; 
              let quantityAfter = quantity - 1;
              this.userService.updateQuantityProduct(cart.product.id,quantityAfter)
                .subscribe(
                  (data: Product) => {
                    this.total();
                    this.totalDis();
                    this.totalAfterDis = this.totalCart - this.totalDiscount;
                  },
                  error => {
                    
                  }
                )
            },
            error => {
              console.log(error);
            }
          );
        }
      },
      error => {
        console.log(error);
      }
    );
  }

  decrease(id: number, index: number){
    this.token = this.tokenStorageService.getToken();
    let cart: Cart;
    let quantity: number;
    if(index > 1){
      this.cartService.getCartByIdCart(this.token, id)
      .subscribe(
        (data: Cart) => {
          cart = data; 
          quantity = cart.product.quantity;
          if(quantity == 0)
          {
            window.confirm("Sorry, no more product in stock!!!");
          }else{
            index--;
            this.cartService.updateCartById(this.token, id, index)
            .subscribe(
              (data: Cart[]) => {
                this.carts = data; 
                let quantityAfter = quantity + 1;
                this.userService.updateQuantityProduct(cart.product.id,quantityAfter)
                  .subscribe(
                    (data: Product) => {
                        this.total();
                        this.totalDis();
                        this.totalAfterDis = this.totalCart - this.totalDiscount;
                    },
                    error => {
                    
                    }
                  )
              },
              error => {
                console.log(error);
              }
            );
          }
        },
        error => {
          console.log(error);
        }
      );
    }
  }

  promotion(price: number){
    if(price > 0)
    {
      return true;
    }else
    {
      return false;
    }
  }

  total(){
    for(let cart of this.carts){
      this.totalCart += (cart.product.price*cart.quantity);
    }
  }

  totalDis(){
    for(let cart of this.carts){
      this.totalDiscount += (cart.product.price*cart.product.promotion/100*cart.quantity);
    }
  }

  checkout(){
    if(this.count == 0)
    {
      window.confirm("Cart is empty!!!");
    }else
    {
      this.router.navigate(['cart/checkout'])
    }
  }

  reloadPage(): void {
    window.location.reload();
  }
}
