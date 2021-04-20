import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "./service/authentication.service";

@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.css']
})
export class AuthenticationComponent implements OnInit {
  allowed = false;

  constructor(private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
  }

  login(email: string, password: string): void {
    console.log("Here is where the login should start...");
    this.authenticationService.checkCredentials(email, password);
    setTimeout(_ => console.log("Authentication details sent."), 2000);
  }

}
