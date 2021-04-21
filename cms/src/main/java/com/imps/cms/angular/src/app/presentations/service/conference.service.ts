import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Conference} from "../model/conference";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ConferenceService {
  private url = "http://localhost:8080/api/conferences";

  constructor(private httpClient: HttpClient) { }

  getConferences(): Observable<Conference[]> {
    return this.httpClient.get<Conference[]>(this.url);
  }
}
