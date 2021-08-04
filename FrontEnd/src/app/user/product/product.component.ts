import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Cart } from 'src/app/cart';
import { ImageDetail } from 'src/app/model/image-detail';
import { Product } from 'src/app/model/product';
import { CartService } from 'src/app/service/cart.service';
import { TokenStorageService } from 'src/app/service/token-storage.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})

//TODO: Add Search function to Product page
export class ProductComponent implements OnInit {

  productName: String = '';
  product!: Product;
  IsPromotion = false;
  imageDetails: Array<ImageDetail> = [];
  imageId!: number;
  token: any;

  constructor(private router : Router, private route: ActivatedRoute, private userService: UserService, private tokenStorageService: TokenStorageService, private cartService: CartService) { }

  ngOnInit(): void {
    this.productName = this.route.snapshot.params['name'];
    this.getProductDetail(this.productName);
  }

  getProductDetail(name: String){
    this.userService.getProductDetail(name)
          .subscribe(
            (data: Product) => {
              this.product = data; 
              if(this.product.promotion > 0)
              {
                this.IsPromotion = true;
              }
              this.imageId = this.product.id;
              this.userService.getImageDetail(this.imageId)
                .subscribe(
                  (data: ImageDetail[]) => {
                    this.imageDetails = data; 
                  },
                  error => {
                    console.log(error);
                  }
                );
            },
            error => {
              console.log(error);
            });
  }

  addToCart(){
    this.token = this.tokenStorageService.getToken();
    console.log(this.token);
    const user = this.tokenStorageService.getUser();
    this.cartService.addToCart(this.token, this.product.id, user.id, 1, this.product.price)
          .subscribe(
            (data: Cart[]) => {
              console.log(data);
              this.router.navigate(['/cart']).then(() => {this.reloadPage()});
            },
            error => {
              this.router.navigate(['/login'])
            });
    
  }
  reloadPage(): void {
    window.location.reload();
  }
}
