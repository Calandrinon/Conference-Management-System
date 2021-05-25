import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Conference} from "../model/conference";
import {Observable} from "rxjs";
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

  getRolesForCurrentUserForConference(conferenceId: number, userId: number): Observable<UserRoleDto>{
    return this.httpClient.get<UserRoleDto>(this.url + "user-roles/" + conferenceId + "/" + userId);
  }


  addAuthorRole(conferenceId: number, userId: number): Observable<UserRoleDto>{
    return this.httpClient.get<UserRoleDto>(this.url + "add-author/" + conferenceId + "/" + userId)
  }

  addListenerRole(conferenceId: number, userId: number): Observable<UserRoleDto>{
    return this.httpClient.get<UserRoleDto>(this.url + "add-listener/" + conferenceId + "/" + userId)
  }

  addPcMemberRole(conferenceId: number, userId: number, token: any): Observable<UserRoleDto>{
    return this.httpClient.get<UserRoleDto>(this.url + "/pc-member/add-pc-member/" + conferenceId + "/" + userId + "/" + token)
  }

  addChairRole(conferenceId: number, userId: number, token: any): Observable<UserRoleDto>{
    return this.httpClient.get<UserRoleDto>(this.url + "chair/add-chair/" + conferenceId + "/" + userId + "/" + token)
  }

  addConference(conference: Conference): Observable<Conference> {
    return this.httpClient.post<Conference>(this.url + "admin/add-conference", conference);
  }

  updateConference(conference: Conference): Observable<Conference> {
    return this.httpClient.post<Conference>(this.url + "chair/update-deadline", conference);
  }
}
