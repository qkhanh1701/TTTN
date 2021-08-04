import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderDetail } from 'src/app/order-detail';
import { TokenStorageService } from 'src/app/service/token-storage.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.css']
})
export class OrderDetailComponent implements OnInit {
  [x: string]: any;
  orderDetails: Array<OrderDetail> = [];
  id!: number;
  total: number = 0;

  constructor(private router : Router,  private route: ActivatedRoute, private userService: UserService, private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.getOrderDetails(this.id);
  }

  getOrderDetails(id: number){
    this.token = this.tokenStorageService.getToken();
    console.log(this.token);
    this.userService.getOrderDetails(this.token,id)
          .subscribe(
            (data: OrderDetail[]) => {
              console.log(data);
              this.orderDetails = data; 
              this.getTotal();
            },
            error => {
              console.log(error);
            });
  }

  getTotal(){
    for(let orderDetail of this.orderDetails){
      this.total += orderDetail.quantity*(orderDetail.amount - orderDetail.amount*orderDetail.discount/100);
    }
  }

}
