import { Injectable } from '@angular/core';
import {Credentials} from "../model/credentials";
import {HttpClient} from "@angular/common/http";
import {Observable, Subscription} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  endpoint = "http://localhost:8080/api/login";

  constructor(private httpClient: HttpClient) { }

  checkCredentials(email: string, password: string): Observable<boolean>{
    let body = new Credentials(email, password);
    console.log("From auth.service");

    return this.httpClient.post<boolean>(this.endpoint, body);
  }
}
