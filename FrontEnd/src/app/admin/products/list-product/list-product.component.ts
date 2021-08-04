import { Product } from '../../../model/product';
import { Category } from '../../../model/category';
import { ActivatedRoute } from '@angular/router';
import { AfterContentChecked, AfterContentInit, AfterViewChecked, AfterViewInit, Component, DoCheck, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { UserService } from 'src/app/service/user.service';
import { TokenStorageService } from 'src/app/service/token-storage.service';

@Component({
  selector: 'app-list-product',
  templateUrl: './list-product.component.html',
  styleUrls: ['./list-product.component.css']
})
export class ListProductComponent implements OnInit {

  products: Array<Product> = [];
  productsByCategory: Array<Product> = [];
  categories: Category[] = [];

  categoryName: string = '';



  filter!: string;

  clickedDelete = false;
  id!: number;
  token!: any;

  pageNumber: number = 1;
  config = {id: 'pagination', itemsPerPage: 5, currentPage: this.pageNumber}

  constructor(private route: ActivatedRoute,
              private userService: UserService,
              private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    this.getAllProducts();
    this.getCategory();
    console.log(this.products);
    console.log(this.categories);
  }

  pageChanged(event: any) {
    this.config.currentPage = event;
  }

  clickedDeleteBtn(id: number) {
    this.clickedDelete = true;
    this.id = id;
  }

  cancelBtnClick() {
    this.clickedDelete = false;
  }

  getProduct(name: String) {
    console.log(name);
    if (name === '') {
      this.getAllProducts();
    } else {
      this.products.length = 0;
      this.userService.getProductByCategory(name)
        .subscribe(
          (data: Category) => {
            this.products = data.products;
          },
          error => {
            console.log(error);
          });

    }
    console.log(this.products);
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

  getAllProducts() {
    this.userService.getAllProducts()
      .subscribe((res) => {
        res.forEach((data) => {
          if (data.deletestatus !== 1) {
            this.products.push(data);
          }
        })
      }, (err) => {
        console.log(err);
      })
  }

  deleteProduct(id: number) {
    this.token = this.tokenStorageService.getToken();
    this.userService.deleteProduct(this.token,id)
      .subscribe(
        (data) => {

        },
        error => {
          console.log(error);
        });
      window.location.reload();
  }

  test() {

  }
}
