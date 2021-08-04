import { AuthService } from './../service/auth.service';
import { Component, OnInit } from '@angular/core';
import { CartService } from '../service/cart.service';
import { TokenStorageService } from '../service/token-storage.service';
import { CountService } from '../service/count.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  username!: string;

  token = '';

  //count!: number;
  count!: number;

  constructor(private tokenStorageService: TokenStorageService, private cartService: CartService, private countService: CountService) {
   }

  ngOnInit(): void {
    this.countService.currentCount.subscribe(count => this.count = count);
  }

  countCartById(){
    this.token = this.tokenStorageService.getToken();
    const user = this.tokenStorageService.getUser();
    this.cartService.countCartById(this.token, user.id)
          .subscribe(
            (data) => {
              this.count = data;
            },
            error => {
              console.log(error);
            }
          );
  }

  isLoggedIn():boolean{
    this.token = this.tokenStorageService.getToken();
    if(this.token == '{}')
    {
      return false;
    }else{    
      const user = this.tokenStorageService.getUser();
      this.username = user.username;
      return true;
    }
}

  logout(): void {
    this.tokenStorageService.signOut();
  }
  
}
