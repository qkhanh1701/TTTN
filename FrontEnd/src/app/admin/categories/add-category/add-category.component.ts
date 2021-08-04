import { UserService } from 'src/app/service/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Category } from 'src/app/model/category';
import { TokenStorageService } from 'src/app/service/token-storage.service';

@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.css']
})
export class AddCategoryComponent implements OnInit {

  name!: string;
  isAddMode!: boolean;
  form!: FormGroup;
  submitted = false;
  notification = false;
  message: string = '';

  categories: Category[] = [];
  thisCategory!: Category;
  pageNumber: number = 1;
  filter: any;
  token!: any;

  constructor(private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private userService: UserService,
              private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    this.name = this.route.snapshot.params['name'];
    this.isAddMode = !this.name;
    
    this.getCategory();

    this.form = this.formBuilder.group({
      name: ['', Validators.required]
    })

    if (!this.isAddMode) {
      this.getCategoryByName(this.name);
    }
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
              this.thisCategory = data;
              this.form.patchValue({
                name: data.categoryName
              })
            },
            error => {
              console.log(error);
            });
  }

  addCategory(name: string) {
    this.token = this.tokenStorageService.getToken();
    let category = new Category();
    category.categoryName = name;
    this.userService.createCategory(this.token, category)
          .subscribe(
            (data) => {
              // this.notification = true;
            },
            error => {
              console.log(error);
            });
  }

  updateCategory(category: Category, name: string) {
    this.token = this.tokenStorageService.getToken();
    category.categoryName = name;
    this.userService.updateCategory(this.token, category.id,category)
          .subscribe(
            (data) => {
              // this.notification = true;
            },
            error => {
              console.log(error);
            });
  }

  get f() {
    return this.form.controls;
  }

  checkCategoryName(name: string) {
    let check = false;
    this.categories.forEach((category) => {
      if (category.categoryName === name) {
        check = true;
      }
    })
    return check;
  }

  onSubmit() {
    this.submitted = true;
    if (this.f.invalid) {
      return;
    } else {
      if (this.f.name.value === '') {
        console.log("aaaaaaaaaa");
        this.notification = true;
        this.message = 'Category name can not be blank!';
      } else if (this.checkCategoryName(this.f.name.value)) {
        this.notification = true;
        this.message = 'Category has already existed!';
      } else {
        if (this.isAddMode) {
          this.addCategory(this.f.name.value);
          this.notification = true;
          this.message = 'Added Category Successfully';
        } else {
          this.updateCategory(this.thisCategory, this.f.name.value);
          this.notification = true;
          this.message = 'Updated Category Successfully';
        }
      }
    }
  }

  cancelSubmit() {
    this.notification = false;
  }

}
