import { formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Cart } from 'src/app/cart';
import { CartService } from 'src/app/service/cart.service';
import { CountService } from 'src/app/service/count.service';
import { TokenStorageService } from 'src/app/service/token-storage.service';


@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  carts: Array<Cart> = [];
  token: any;
  total: number = 0;
  dataForm!: FormGroup;
  today= new Date();
  jstoday = '';
  count!: any;
  
  constructor(private cartService: CartService, private tokenStorageService: TokenStorageService, private fb: FormBuilder, private router : Router, private countService: CountService) { }

  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();
    console.log(user.id);
    this.infoForm();
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
              this.getTotal();
            },
            error => {
              console.log(error);
            });
  }

  getTotal(){
    for(let cart of this.carts){
      this.total += cart.quantity*(cart.product.price - cart.product.price*cart.product.promotion/100);;
    }
  }

  checkOut(){
    this.token = this.tokenStorageService.getToken();
    this.jstoday = formatDate(this.today, 'yyyy-MM-dd', 'en-US', '+0530');
    const user = this.tokenStorageService.getUser();
    this.cartService.checkout(this.token, this.jstoday, this.total, this.dataForm.get('receiver')?.value, this.dataForm.get('address')?.value, this.dataForm.get('phoneNumber')?.value, 1, user.id)
        .subscribe(
          (data) => {
            console.log(data);
            this.cartService.countCartById(this.token, user.id)
                                  .subscribe(
                                    (data) => {
                                      this.count = data;
                                      this.countService.changeCount(this.count);
                                    },
                                    error => {
                                      console.log(error);
                                    }
                                  );
            this.router.navigate(['/orderlist']);
          },
          error => {
            console.log(error);
          });
  }

  infoForm(){
    /*Create Form group*/
    this.dataForm = this.fb.group({
      receiver: ['', [Validators.required]],   
      address: ['', [Validators.required]],
      phoneNumber: ['', [Validators.required, Validators.pattern("^[_0-9]{10}")]],
    })
  }
  
}
