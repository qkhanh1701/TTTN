import { AuthService } from './../../../service/auth.service';
import { ImageDetail } from './../../../model/image-detail';
import { Product } from './../../../model/product';
import { UserService } from 'src/app/service/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { FullProduct } from 'src/app/model/full-product';
import { Category } from 'src/app/model/category';
import { TokenStorageService } from 'src/app/service/token-storage.service';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})

export class EditProductComponent implements OnInit {
  form!: FormGroup;
  submitted = false;
  productName!: string;
  product!: Product;
  imageDetails: Array<ImageDetail> = [];
  imageId!: number;
  fullProduct!: FullProduct;
  list : ImageDetail[] = [];

  categories: Category[] = [];
  categoryName!: string;
  category!: Category;

  notification = false;
  message = '';
  token!: any;

  constructor(private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private userService: UserService,
    private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    this.productName = this.route.snapshot.params['name'];

    this.form = this.formBuilder.group({
      id: [{value: '', disabled: true}],
      name: ['', Validators.required],
      quantity: ['', [Validators.required, Validators.min(0)]],
      price: ['', [Validators.required, Validators.min(0)]],
      description: ['', Validators.required],
      promotion: ['', [Validators.required, Validators.max(100), Validators.min(0)]],
      image: ['', [Validators.required]],
      subImage1: ['', [Validators.required]],
      subImage2: ['', [Validators.required]],
      subImage3: ['', [Validators.required]]
    })

    // this.getCategory();

    this.userService.getProductDetail(this.productName)
          .subscribe(
            (data: Product) => {
              this.product = data; 
              this.imageId = this.product.id;
              this.userService.getCategoryById(data.categoryId)
                .subscribe(s => {
                  this.categoryName = s.categoryName;
                })
              this.userService.getImageDetail(this.imageId)
                .subscribe(
                  (data: ImageDetail[]) => {
                    this.imageDetails = data; 
                    this.form.patchValue(this.product);
                    this.form.patchValue({
                      subImage1: this.imageDetails[0].image,
                      subImage2: this.imageDetails[1].image,
                      subImage3: this.imageDetails[2].image
                    })
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

  get f() {
    return this.form.controls;
  }

  getCategory(){
    this.userService.getCategory()
          .subscribe(
            (data: Category[]) => {
              data.forEach(s => {
                if (s.deletestatus !== 1) {
                  this.categories.push(s);
                }
              });
            },
            error => {
              console.log(error);
            });
  }

  getCategoryByName(name: string) {
    this.userService.getCategoryByName(name)
          .subscribe(
            (data: Category) => {
              this.category = data; 
            },
            error => {
              console.log(error);
            });
  }

  updateProduct(id: number, product: Product) {
    this.token = this.tokenStorageService.getToken();
    this.userService.updateProduct(this.token, id, product)
          .subscribe(
            (data) => {
              // this.userService.updateProductDetails(id, this.imageDetails);           
            },
            error => {
              console.log(error);
            });
    
    
    this.userService.updateProductDetails(this.token, this.product.id, this.list)
          .subscribe(
            (data) => {

            },
            error => {
              console.log(error);
            }
          );
  }

  onSubmit() {
    this.submitted = true;
    if (this.f.invalid) {
      return;
    } else {
      this.product.name = this.f.name.value;
      this.product.price = this.f.price.value;
      this.product.description = this.f.description.value;
      this.product.image = this.f.image.value;
      this.product.promotion = this.f.promotion.value;
      this.product.deletestatus = 0;

      let image1 = new ImageDetail();
      image1.imageId = this.product.id;
      image1.image = this.f.subImage1.value;

      let image2 = new ImageDetail();
      image2.imageId = this.product.id;
      image2.image = this.f.subImage2.value;

      let image3 = new ImageDetail();
      image3.imageId = this.product.id;
      image3.image = this.f.subImage3.value;

      this.list.push(image1);
      this.list.push(image2);
      this.list.push(image3);

      console.log(this.f.subImage1.value, this.f.subImage2.value, this.f.subImage3.value)
      console.log(this.list);
      
      this.updateProduct(this.product.id, this.product);
      this.notification = true;
      this.message = 'Updated Product Successfully';
    }
    
  }

}
