import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContactComponent } from './contact/contact.component';
import { HomeComponent } from './home/home.component';
import { PresentationsComponent } from './presentations/presentations.component';
import {ProfileComponent} from "./profile/profile.component";
import {AuthenticationComponent} from "./authentication/login/authentication.component";

const routes: Routes = [
  {path: "home", component: HomeComponent},
  {path: "presentations", component: PresentationsComponent},
  {path: "contact", component: ContactComponent},
  {path: "auth", component: AuthenticationComponent},
  {path: "profile", component: ProfileComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
