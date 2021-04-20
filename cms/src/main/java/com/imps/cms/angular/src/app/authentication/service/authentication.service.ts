import { Injectable } from '@angular/core';
import {Credentials} from "../model/credentials";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  endpoint = "http://localhost:8080/api/login";

  constructor(private httpClient: HttpClient) { }

  checkCredentials(email: string, password: string): boolean {
    let body = new Credentials(email, password);
    let returnValue = false;
    console.log("From auth.service");
    this.httpClient.post<Credentials>(this.endpoint, body).subscribe(response => {
      console.log("Response after login request: ", response);
      //??? returnValue = response;
    });

    console.log("before return");
    return returnValue;
  }
}
