import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Section} from "../model/section-model";

@Injectable({
  providedIn: 'root'
})
export class SectionService {
  private url = "http://localhost:8080/api/";

  constructor(private httpClient: HttpClient) { }

  getSections(): Observable<Section[]> {
    console.log("it gets to service")
    return this.httpClient.get<Section[]>(this.url + "chair/sections");
  }
}
