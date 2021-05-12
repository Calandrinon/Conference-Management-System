import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { NavigationBarComponent } from './navigation-bar/navigation-bar.component';
import { PresentationsComponent } from './presentations/presentations.component';
import { ContactComponent } from './contact/contact.component';
import {AuthenticationService} from "./authentication/service/authentication.service";
import { HttpClientModule, HttpClient} from "@angular/common/http";
import { ProfileComponent } from './profile/profile.component';
import {AuthenticationComponent} from "./authentication/login/authentication.component";
import { RegisterComponent } from './authentication/register/register.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {CookieService} from "ngx-cookie-service";
import { UserPresentationsComponent } from './user-presentations/user-presentations.component';
import { ListenerPageComponent } from './listener/listener-page/listener-page.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NavigationBarComponent,
    AuthenticationComponent,
    PresentationsComponent,
    ContactComponent,
    ProfileComponent,
    RegisterComponent,
    UserPresentationsComponent,
    ListenerPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [AuthenticationService, CookieService],
  bootstrap: [AppComponent]
})
export class AppModule { }
