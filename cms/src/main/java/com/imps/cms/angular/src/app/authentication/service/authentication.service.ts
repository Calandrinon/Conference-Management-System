import { Injectable } from '@angular/core';
import {Credentials} from "../model/credentials";
import {HttpClient} from "@angular/common/http";
import {Observable, Subscription} from "rxjs";
import {UserDto} from "../model/UserDto";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  endpoint = "http://localhost:8080/api/login";

  constructor(private httpClient: HttpClient) { }

  checkCredentials(email: string, password: string): Observable<UserDto>{
    let body = new Credentials(email, password);
    console.log("From auth.service");
    return this.httpClient.post<UserDto>(this.endpoint, body);
  }
}
