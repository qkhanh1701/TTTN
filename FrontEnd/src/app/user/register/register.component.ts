import { Component, OnInit } from '@angular/core';
import { AbstractControl, AsyncValidatorFn, FormBuilder, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth.service';
import { TokenStorageService } from 'src/app/service/token-storage.service';
import { UserService } from 'src/app/service/user.service';
import { Observable, of } from 'rxjs';
import { delay, map, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  dataForm!: FormGroup;
  none: boolean = true;
  roles: String[] = ["user"];

  constructor(private fb: FormBuilder, private authService: AuthService, private tokenStorage: TokenStorageService, private router:Router, private userService: UserService) { }

  ngOnInit(): void {
    this.infoForm();
  }

  infoForm(){
    /*Create Form group*/
    this.dataForm = this.fb.group({
      username: ['', [Validators.required]],   
      firstname: ['', [Validators.required]],
      lastname: ['', [Validators.required]],   
      email: ['', [Validators.required, Validators.pattern("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
      + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")], [this.emailExistsValidator()]], 
      phone: ['', [Validators.required]],   
      address: ['', [Validators.required]],
      password: ['', [Validators.required]],
      pwdd: ['', [Validators.required]],
      deletestatus: [0],
    },
    {
      validators: this.MustMatch('password', 'pwdd')
    })
  }

  onSubmit() {
    const val = this.dataForm.value;
    this.addData();
  }

  addData() {
    console.log(this.dataForm.get("deletestatus")?.value);
    console.log(this.roles)
    this.authService.register(this.dataForm.value, this.roles).
    subscribe( (data: any) => {
      console.log("Registion success");
      this.router.navigate(['/login']);
    });
  }

  private emailExistsValidator(): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      return of(control.value).pipe(
        delay(500),
        switchMap((email: any) => this.userService.doesEmailExist(email).pipe(
          map(emailExists => emailExists ? { emailExists: true } : null)
        ))
      );
    };
  }

  MustMatch(controlName: string, matchingControlName:string){
    return(formGroup: FormGroup)=>{
      const control = formGroup.controls[controlName];
      const matchingControl = formGroup.controls[matchingControlName];
      if(matchingControl.errors && !matchingControl.errors.MustMatch){
        return;
      }
      if(control.value !== matchingControl.value){
        matchingControl.setErrors({MustMatch:true});
      }else{
        matchingControl.setErrors(null);
      }
    }
  }
  
}
