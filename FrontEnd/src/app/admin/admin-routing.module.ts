import { HomeComponent } from './home/home.component';
import { ListCategoriesComponent } from './categories/list-categories/list-categories.component';
import { AdminComponent } from './admin.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListProductComponent } from './products/list-product/list-product.component';
import { EditProductComponent } from './products/edit-product/edit-product.component';
import { AddProductComponent } from './products/add-product/add-product.component';
import { AddCategoryComponent } from './categories/add-category/add-category.component';
import { OrderlistComponent } from './order/orderlist/orderlist.component';
import { OrderDetailComponent } from './order/order-detail/order-detail.component';
import { UsersComponent } from './user_/users/users.component';
import { EditUserComponent } from './user_/edit-user/edit-user.component';
import { PageNotFoundComponent } from '../page-not-found/page-not-found.component';

const routes: Routes = [
  { path: 'admin', component: AdminComponent, 
      children: [
        {path: '', component: HomeComponent},
        {path: 'products', component: ListProductComponent},
        {path: 'edit/:name', component: EditProductComponent},
        {path: 'add-product', component: AddProductComponent},
        {path: 'categories', component: ListCategoriesComponent},
        {path: 'add-category', component: AddCategoryComponent},
        {path: 'edit-category/:name', component: AddCategoryComponent},
        {path: 'users', component: UsersComponent},
        {path: 'edit-user/:id', component: EditUserComponent},
        {path: 'orderlist', component: OrderlistComponent},
        {path: 'orderDetail/:id', component: OrderDetailComponent },
        // { path: '**', component: PageNotFoundComponent}
      ]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
