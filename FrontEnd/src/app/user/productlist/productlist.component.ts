import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Category } from 'src/app/model/category';
import { Product } from 'src/app/model/product';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-productlist',
  templateUrl: './productlist.component.html',
  styleUrls: ['./productlist.component.css']
})
export class ProductlistComponent implements OnInit {

  categories: Array<Category> = [];
  products: Array<Product> = [];
  namePage: String = 'product/';
  config: any;
  currentSelectedPage:number = 0;

  constructor(private userService: UserService, private router : Router) { }

  ngOnInit(): void {
    this.getCategory();
    this.getProduct("LAPTOP");
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
            
  }

  getProduct(name: String){
      this.userService.getProductByCategory(name)
        .subscribe(
          (data: Category) => {
            this.products = data.products;
          },
          error => {
            console.log(error);
          });
          this.config = {
            itemsPerPage: 5,
            currentPage: 1,
            totalItems: this.products.values.length
        };
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

  ridrect(name: String)
  {
    this.router.navigate([`${this.namePage}${name}`]);
  }
  
  pageChanged(event: any){
    this.config.currentPage = event;
  }
}
