import { UserService } from 'src/app/service/user.service';
import { AuthService } from './../../../service/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from './../../../model/product';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Category } from 'src/app/model/category';
import { ImageDetail} from 'src/app/model/image-detail';
import { TokenStorageService } from 'src/app/service/token-storage.service';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {

  form!: FormGroup;
  subForm!: FormGroup;
  submitted = false;
  submitted1 = false;

  productName!: string;
  product!: Product;
  products: Array<Product> = [];
  categories: Category[] = [];
  categoryName: string = '';
  category!: Category;
  token: any;

  notification = false;
  clickedCancel = false;
  message!: string;

  list: ImageDetail[] = [];

  constructor(private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private userService: UserService,
    private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    this.getAllProducts();

    this.form = this.formBuilder.group({
      id: [{value: '', disabled: true}],
      name: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(0)]],
      quantity: ['', [Validators.required, Validators.min(0)]],
      description: ['', Validators.required],
      promotion: ['', [Validators.required, Validators.max(100), Validators.min(0)]],
    })

    this.subForm = this.formBuilder.group({
      image: ['', [Validators.required]],
      subImage1: ['', [Validators.required]],
      subImage2: ['', [Validators.required]],
      subImage3: ['', [Validators.required]]
    })

    this.getCategory();
    
  }

  get f() {
    return this.form.controls;
  }

  get sf() {
    return this.subForm.controls;
  }

  getAllProducts() {
    this.userService.getAllProducts()
      .subscribe((res) => {
        res.forEach((data) => {
          this.products.push(data);
        })
        console.log(this.products);
      }, (err) => {
        console.log(err);
      })
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
              console.log(data);
              console.log(this.categoryName);
            },
            error => {
              console.log(error);
            });
  }

  addProduct(categoryName: string) {
    let product = new Product();
    product.name = this.f.name.value;
    product.quantity = this.f.quantity.value;
    product.price = this.f.price.value;
    product.promotion = this.f.promotion.value;
    product.description = this.f.description.value;
    // product.image = this.f.image.value;
    product.deletestatus = 0;
    product.categoryId = this.category.id;
    console.log(product);
    this.token = this.tokenStorageService.getToken();
    this.userService.createProduct(this.token, product)
        .subscribe((data: string) => {
          console.log("thêm thành công sẩn phẩm");
          this.userService.getProductDetail(product.name)
              .subscribe((data) => {
                console.log("data worked");
                this.product = data;
              }, (err) => {
                console.log(err);
              })
        }, err => {
          console.log(err);
          this.userService.getProductDetail(product.name)
              .subscribe((data) => {
                console.log("data worked");
                this.product = data;
              }, (err) => {
                console.log(err);
              })
        })
  }

  getProductByName(name: string) {
    this.userService.getProductDetail(name)
        .subscribe((data) => {
          this.product = data;
        }, (err) => {
          console.log(err);
        })
  }

  onSubmit() {
    this.submitted = true;
    if (this.f.invalid) {
      return;
    } 
    this.products.forEach((product) => {
      if (product.name === this.f.name.value) {
        this.notification = !this.notification;
        this.message = 'This product has already existed!';
      }
    }
    )
    this.addProduct(this.categoryName);
    console.log('123');
    console.log(this.product);
  }

  btnNoClicked() {
    this.clickedCancel = false;
  }

  navigateToProducts() {
    this.router.navigate(['../products'], {relativeTo: this.route});
  }

  cancelFormClick() {
    if (this.categoryName !== '' || this.f.name.value !== '' || this.f.price.value !== '' 
          || this.f.description.value !== '' || this.f.promotion.value !== '') {
            this.clickedCancel = true;  
          }
    else {
      this.navigateToProducts();
    }
  }

  cancelSubFormClick() {
    this.clickedCancel = true;
  }

  deleteProductAndCancel(id: number) {
    this.token = this.tokenStorageService.getToken();
    this.userService.deleteProduct(this.token,id)
        .subscribe((data) => {
          console.log(data);
          this.navigateToProducts();
        }, (err) => {
          console.log(err);
          this.navigateToProducts();
        })
  }

  createListImageDetails() {
    let image1 = new ImageDetail();
    image1.imageId = this.product.id;
    image1.image = this.sf.subImage1.value;

    let image2 = new ImageDetail();
    image2.imageId = this.product.id;
    image2.image = this.sf.subImage2.value;

    let image3 = new ImageDetail();
    image3.imageId = this.product.id;
    image3.image = this.sf.subImage3.value;

    this.list.push(image1);
    this.list.push(image2);
    this.list.push(image3);

    // let imageDetail = new ImageDetail();
    // imageDetail.imageId = this.product.id;
    // imageDetail.image = this.sf.subImage1.value;
    // this.list.push(imageDetail);
    // imageDetail.image = this.sf.subImage2.value;
    // this.list.push(imageDetail);
    // imageDetail.image = this.sf.subImage3.value;
    // this.list.push(imageDetail);
    this.product.image = this.sf.image.value;
    this.token = this.tokenStorageService.getToken();
    this.userService.updateProduct(this.token, this.product.id, this.product)
        .subscribe((res) => {
            this.userService.createImageDetail(this.list)
            .subscribe((data) => {
              console.log(data)
            }, (err) => {
              console.log(err);
            })
          }, (err) => {
            console.log(err);
            this.userService.createImageDetail(this.list)
            .subscribe((data) => {
              console.log(data)
            }, (err) => {
              console.log(err);
            })
          }
        )
  }

  onSubSubmit() {
    this.submitted1 = true;
    if (this.sf.invalid) {
      return;
    } 
    if (this.sf.image.value !== '' && this.sf.subImage1.value !== '' && this.sf.subImage2.value !== '' && this.sf.subImage3.value !== '') {
      this.notification = true;
      this.message = 'Added Product Successfully!'
      this.createListImageDetails();
    }
  }
}
