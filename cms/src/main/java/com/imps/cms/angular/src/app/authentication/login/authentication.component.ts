import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {AuthenticationService} from "../service/authentication.service";
import {UserDto} from "../model/UserDto";
import {NavigationBarComponent} from "../../navigation-bar/navigation-bar.component";

@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.css']
})
export class AuthenticationComponent implements OnInit {
  authenticated: boolean;

  constructor(private authenticationService: AuthenticationService,
              private router: Router) { }

  ngOnInit(): void {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  }

  login(email: string, password: string): void {
    console.log("Here is where the login should start...");
    this.authenticationService.checkCredentials(email, password).subscribe(
      (response: UserDto) => {
        if(response !== null){
          this.authenticated = true
          console.log(JSON.stringify(response))
          localStorage.setItem('current-user', JSON.stringify(response))
          document.defaultView.location.reload()
          this.router.navigate(['/profile']).then(p => {window.location.reload(); return true});
        }
        else this.authenticated = false
      }
    )
  }
}
