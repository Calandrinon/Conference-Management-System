import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {Observable} from "rxjs";
import {AuthenticationService} from "../service/authentication.service";

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
  }

  login(email: string, password: string): void {
    console.log("Here is where the login should start...");
    let response = this.authenticationService.checkCredentials(email, password);
    response.subscribe(result => {
      console.log("AuthComponent: response from the server -> ", result);
      if (result) {
        // redirect to route /profile
        this.authenticated = true;
        this.router.navigate(["/profile"]);
      } else {
        // display error message
        this.authenticated = false;
      }
    });
  }

}
