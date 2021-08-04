import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { AuthService } from 'src/app/service/auth.service';
import { TokenStorageService } from 'src/app/service/token-storage.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-change-info',
  templateUrl: './change-info.component.html',
  styleUrls: ['./change-info.component.css']
})
export class ChangeInfoComponent implements OnInit {

  user!: User;
  id!: number;
  dataForm!: FormGroup;

  notification = false;
  message!: string;
  submitted = false;
  token!: any;
  constructor(private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private userService: UserService,
    private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();
    this.id = user.id;

    this.dataForm = this.formBuilder.group({
      id: [{value: '', disabled: true}],
      username: ['', Validators.required],
      password: [{value: '', disabled: true}],
      email: ['', Validators.required],
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      phone: ['', Validators.required],
      address: ['', Validators.required],
    })
    console.log(this.id);
    this.getUserById(this.id);
  }

  getUserById(id: number) {
    this.token = this.tokenStorageService.getToken();
    this.userService.getUserById(this.token, id)
      .subscribe((data) => {
        this.user = data;
        console.log(this.user);
        this.dataForm.patchValue(this.user);
      }, (err) => {
        console.log(err);
      })
  }

  get f() {
    return this.dataForm.controls;
  }

  updateUser(id: number, user: User) {
    this.token = this.tokenStorageService.getToken();
    this.userService.updateUser(this.token, id, user)
          .subscribe(
            (data: User) => {
              console.log("aaa");
              this.router.navigate(['/']);
              // this.userService.updateProductDetails(id, this.imageDetails);           
            },
            error => {
              console.log(error);
            });
  }

  onSubmit() {
    // alert("123");
    this.submitted = true;
    if (this.f.invalid) {
      alert("456");
      return;
    } else {
      this.user.email = this.f.email.value;
      this.user.username = this.f.username.value;
      this.user.firstname = this.f.firstname.value;
      this.user.lastname = this.f.lastname.value;
      this.user.phone = this.f.phone.value;
      this.user.address = this.f.address.value;
      console.log(this.user);
      
      this.updateUser(this.user.id, this.user);
    }
  }
}
