import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Conference} from "../model/conference";
import {Observable} from "rxjs";
import {Deadline} from "../model/deadline";
import {UserRoleDto} from "../model/user-role-dto";

@Injectable({
  providedIn: 'root'
})
export class ConferenceService {
  private url = "http://localhost:8080/api/";

  constructor(private httpClient: HttpClient) { }

  getConferences(): Observable<Conference[]> {
    return this.httpClient.get<Conference[]>(this.url + "conferences");
  }

  getDeadlinesForConferenceId(id: number): Observable<Deadline[]>{
    return this.httpClient.get<Deadline[]>(this.url + "deadlines/" + id);
  }

  getRolesForCurrentUserForConference(conferenceId: number, userId: number): Observable<UserRoleDto[]>{
    return this.httpClient.get<UserRoleDto[]>(this.url + "userRoles/" + conferenceId + "/" + userId);
  }


}
