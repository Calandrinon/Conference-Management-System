import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Credentials} from "../model/credentials";
import {UserDto} from "../model/UserDto";

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  endpoint = "http://localhost:8080/api/registerUser"

  constructor(private httpClient: HttpClient) { }

  registerAccount(userDto: UserDto): Observable<UserDto>{

    console.log("From register.service");

    return this.httpClient.post<UserDto>(this.endpoint, userDto);
  }
}
