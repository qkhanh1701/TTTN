import { Component, OnInit } from '@angular/core';
import { Checkout } from 'src/app/checkout';
import { User } from 'src/app/model/user';
import { TokenStorageService } from 'src/app/service/token-storage.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit {
  token!: any;
  users: User[] = [];
  products: Array<Checkout> = [];
  statistics: number[] = [];

  constructor(private userService: UserService,
              private tokenStorageService: TokenStorageService) { }

  getTop5Users(){
    this.token = this.tokenStorageService.getToken();
    this.userService.getTop5Users(this.token)
          .subscribe(
            (data: User[]) => {
              console.log(data);
              this.users = data; 
              console.log(this.users);
            },
            error => {
              console.log(error);
            });
  }

  getTop10Orders() {
    this.token = this.tokenStorageService.getToken();
    this.userService.getTop10Orders(this.token)
          .subscribe((data: Checkout[]) => {
            this.products = data;
            console.log(this.products);
          }, (err) => {
            console.log(err);
          })
  }

  getStatistics() {
    this.token = this.tokenStorageService.getToken();
    this.userService.getStatistics(this.token)
          .subscribe((data: number[]) => {
            this.statistics = data;
            console.log(this.statistics);
          }, (err) => {
            console.log(err);
          })
  }

  ngOnInit(): void {
    this.getTop5Users();
    this.getTop10Orders();
    this.getStatistics();
  }

}
