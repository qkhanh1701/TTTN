import { Component, DoCheck, OnInit } from '@angular/core';
import { AuthService } from './service/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

//TODO: Fix Bugs in UI of user
export class AppComponent {
  

  constructor(public authService: AuthService) {}
  

  title = 'EcommerceWeb';
}
