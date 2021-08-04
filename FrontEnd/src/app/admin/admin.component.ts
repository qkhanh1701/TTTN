import { Category } from './../model/category';
import { TokenStorageService } from './../service/token-storage.service';
import { Component, OnInit } from '@angular/core';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  clickCategory = false;

  categories: Array<Category> = [];

  categoryName: string = '';
  constructor(private tokenStorageService: TokenStorageService,
              private userService: UserService) { }

  ngOnInit(): void {
    this.getCategory();
  }

  logout(): void {
    this.tokenStorageService.signOut();
  }

  getCategory(){
    this.userService.getCategory()
          .subscribe(
            (data: Category[]) => {
              this.categories = data; 
            },
            error => {
              console.log(error);
            });
    console.log(this.categories);
  }

  getCategoryName(categoryName: String) {
    this.categoryName = <string>categoryName;
    console.log(this.categoryName);
    console.log(categoryName);
  } 

}
