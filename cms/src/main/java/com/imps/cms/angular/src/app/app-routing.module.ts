import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContactComponent } from './contact/contact.component';
import { HomeComponent } from './home/home.component';
import { PresentationsComponent } from './presentations/presentations.component';
import {ProfileComponent} from "./profile/profile.component";
import {AuthenticationComponent} from "./authentication/login/authentication.component";
import {RegisterComponent} from "./authentication/register/register.component";
import {UserPresentationsComponent} from "./user-presentations/user-presentations.component";
import {AppComponent} from "./app.component";
import {ListenerPageComponent} from "./listener/listener-page/listener-page.component";
import {AdminPageComponent} from "./admin-page/admin-page.component";
import {ChairPageComponent} from "./chair-page/chair-page.component";
import {PcMemberPageComponent} from "./pc-member-page/pc-member-page.component";
import {AuthorPageComponent} from "./author-page/author-page.component";

const routes: Routes = [
  {path: "home", component: HomeComponent},
  {path: "reload", component: AppComponent},
  {path: "presentations", component: PresentationsComponent},
  {path: "contact", component: ContactComponent},
  {path: "auth", component: AuthenticationComponent},
  {path: "register", component: RegisterComponent},
  {path: "profile", component: ProfileComponent},
  {path: "user-presentations", component: UserPresentationsComponent},
  {path: "admin-page", component: AdminPageComponent},
  {path: "chair-page", component: ChairPageComponent},
  {path: "pc-member-page", component: PcMemberPageComponent},
  {path: "author-page", component: AuthorPageComponent},
  {path: "listener-page", component: ListenerPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
