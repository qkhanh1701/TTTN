import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model/user';
import { TokenStorageService } from 'src/app/service/token-storage.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})

export class UsersComponent implements OnInit {
  users: User[] = [];
  filter: any;
  pageNumber: number = 1;
  clickedDelete = false;
  id!: number;
  token!: any;

  constructor(private userService: UserService,
              private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    this.getAllUsers();
  }

  getAllUsers(){
    this.token = this.tokenStorageService.getToken();
    this.userService.getAllUsers(this.token)
          .subscribe(
            (data: User[]) => {
              console.log(data);
              this.users = data; 
              this.users.forEach((user, index) => {
                let temp = this.tokenStorageService.getUser();
                if (user.deletestatus === 1) {
                  this.users.splice(index, 1);
                }
                if (user.id === temp.id) {
                  this.users.splice(index, 1);
                }
              })
              console.log(this.users);
            },
            error => {
              console.log(error);
            });
  }

  deleteUser(id: number) {
    this.token = this.tokenStorageService.getToken();
    this.userService.deleteUser(this.token, id)
      .subscribe(
        (data) => {
          
        },
        error => {
          console.log(error);
        });
      window.location.reload();
  }
}