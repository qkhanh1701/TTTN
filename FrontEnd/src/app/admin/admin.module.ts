import { HomeComponent } from './home/home.component';
import { AddCategoryComponent } from './categories/add-category/add-category.component';
import { ListCategoriesComponent } from './categories/list-categories/list-categories.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './admin.component';
import { ListProductComponent } from './products/list-product/list-product.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxPaginationModule } from 'ngx-pagination';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import { EditProductComponent } from './products/edit-product/edit-product.component';
import { AddProductComponent } from './products/add-product/add-product.component';
import { OrderlistComponent } from './order/orderlist/orderlist.component';
import { OrderDetailComponent } from './order/order-detail/order-detail.component';
import { UsersComponent } from './user_/users/users.component';
import { EditUserComponent } from './user_/edit-user/edit-user.component';


@NgModule({
  declarations: [
    AdminComponent,
    ListProductComponent,
    EditProductComponent,
    AddProductComponent,
    ListCategoriesComponent,
    AddCategoryComponent,
    OrderlistComponent,
    OrderDetailComponent,
    UsersComponent,
    EditUserComponent,
    HomeComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    NgxPaginationModule, 
    Ng2SearchPipeModule
  ],
  providers: [],
  bootstrap: [AdminComponent]
})
export class AdminModule { }
