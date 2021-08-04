import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AdminComponent } from './admin/admin.component';
import { AdminModule } from './admin/admin.module';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomeComponent } from './user/home/home.component';
import { LoginComponent } from './user/login/login.component';
import { RegisterComponent } from './user/register/register.component';
import { CartComponent } from './user/cart/cart.component';
import { CheckoutComponent } from './user/checkout/checkout.component';
import { ProductComponent } from './user/product/product.component';
import { ProductlistComponent } from './user/productlist/productlist.component';
import { OrderlistComponent } from './user/orderlist/orderlist.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { OrderDetailComponent } from './user/order-detail/order-detail.component';
import { ChangeInfoComponent } from './user/change-info/change-info.component';

import { LoginGuard } from './guard/login.guard';
import { LoginInterceptor } from './interceptor/login.interceptor';
import { UserService } from './service/user.service';
import { AuthService } from './service/auth.service';
import { CartService } from './service/cart.service';
import { CountService } from './service/count.service';
import { TokenStorageService } from './service/token-storage.service';
import { AuthInterceptor } from './service/auth.interceptor';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    FooterComponent,
    CartComponent,
    LoginComponent,
    RegisterComponent,
    CheckoutComponent,
    ProductComponent,
    ProductlistComponent,
    OrderlistComponent,
    PageNotFoundComponent,
    OrderDetailComponent,
    ChangeInfoComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    AdminModule,
    BrowserAnimationsModule,
    NgxPaginationModule
  ],
  // providers: [UserService, AuthService, CartService, CountService, TokenStorageService ,LoginGuard, 
  //   {provide: HTTP_INTERCEPTORS, useClass:  AuthInterceptor, multi: true}
  // ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
