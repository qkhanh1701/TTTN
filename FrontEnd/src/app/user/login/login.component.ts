import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth.service';
import { CartService } from 'src/app/service/cart.service';
import { CountService } from 'src/app/service/count.service';
import { TokenStorageService } from 'src/app/service/token-storage.service';
import { UserService } from 'src/app/service/user.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  dataForm!: FormGroup;
  namePage: String = '';
  roles: string[] = [];
  token: string = '';
  IsLogin = false;
  count!: any;

  constructor(private fb: FormBuilder, private authService: AuthService, private tokenStorage: TokenStorageService, private router:Router, private tokenStorageService: TokenStorageService, private cartService: CartService, private countService: CountService) { }

  ngOnInit(): void {
    this.infoForm();
  }

  infoForm(){
    /*Create Form group*/
    this.dataForm = this.fb.group({
      email: ['', [Validators.required]],   
      password: ['', [Validators.required]]
    })
  }

  onSubmit(): void {
    this.authService.login(this.dataForm.value).subscribe(
      data => {
        this.tokenStorage.saveUser(data);        
        this.token =  this.tokenStorage.getUser().token;
        this.tokenStorage.saveToken(this.token);
        this.roles = this.tokenStorage.getUser().roles;
        const user = this.tokenStorageService.getUser();
        if(this.roles[0] == "ROLE_USER")
        {
          this.cartService.countCartById(this.token, user.id)
                                  .subscribe(
                                    (data) => {
                                      this.count = data;
                                      this.countService.changeCount(this.count);
                                    },
                                    error => {
                                      console.log(error);
                                    }
                                  );
          this.router.navigate([`${this.namePage}`]);
        }    
        else {
          this.router.navigate(['admin']);
        }
      },
      err => {  
        this.IsLogin = true;
      }
    );
  }
  
}
