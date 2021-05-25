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
import { AdminPageComponent } from './admin-page/admin-page.component';
import { ChairPageComponent } from './chair-page/chair-page.component';
import { PcMemberPageComponent } from './pc-member-page/pc-member-page.component';

import { FileUploadComponent } from './utilities/file-upload/file-upload.component';
import { FileViewComponent } from './utilities/file-view/file-view.component';
import { ProposalControlComponent } from './utilities/proposal-control/proposal-control.component';
import { AuthorPageComponent } from './author-page/author-page.component';


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
    AdminPageComponent,
    ChairPageComponent,
    PcMemberPageComponent,
    FileUploadComponent,
    FileViewComponent,
    ProposalControlComponent,
    AuthorPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [AuthenticationService, CookieService],
  bootstrap: [AppComponent]
})
export class AppModule { }
