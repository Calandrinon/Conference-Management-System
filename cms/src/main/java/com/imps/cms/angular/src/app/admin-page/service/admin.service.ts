import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Conference} from "../../presentations/model/conference";
import {Observable} from "rxjs";
import {Deadline} from "../../presentations/model/deadline";

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private url = "http://localhost:8080/api/";
  constructor(private httpClient: HttpClient) { }

  addConference(conference: Conference): Observable<Conference> {
    return this.httpClient.post<Conference>(this.url + "admin/add-conference", conference);
  }

  addDeadline(deadline: Deadline): Observable<Deadline> {
    return this.httpClient.post<Deadline>(this.url + "admin/add-deadline", deadline);
  }
}
