import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { CartComponent } from './user/cart/cart.component';
import { ChangeInfoComponent } from './user/change-info/change-info.component';
import { CheckoutComponent } from './user/checkout/checkout.component';
import { HomeComponent } from './user/home/home.component';
import { LoginComponent } from './user/login/login.component';
import { OrderDetailComponent } from './user/order-detail/order-detail.component';
import { OrderlistComponent } from './user/orderlist/orderlist.component';
import { ProductComponent } from './user/product/product.component';
import { ProductlistComponent } from './user/productlist/productlist.component';
import { RegisterComponent } from './user/register/register.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'cart', component: CartComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'cart/checkout', component: CheckoutComponent },
  { path: 'product/:name', component: ProductComponent },
  { path: 'productlist', component: ProductlistComponent },
  { path: 'productlist/:name', component: ProductlistComponent },
  { path: 'orderlist', component: OrderlistComponent },
  { path: 'orderDetail/:id', component: OrderDetailComponent },
  { path: 'changeInfo', component: ChangeInfoComponent },
  //TODO: Add interceptor and Page Not Found Page
  // { path: '**', component: PageNotFoundComponent},

  { path: '', redirectTo: 'admin', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
