import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {UserDto} from "../../authentication/model/UserDto";
import {UserRoleDto} from "../../presentations/model/user-role-dto";
import {Invitation} from "../../presentations/model/invitation";

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private url = "http://localhost:8080/api/";
  constructor(private httpClient: HttpClient) { }

  getUsers(): Observable<UserDto[]> {
    return this.httpClient.get<UserDto[]>(this.url + "users/all")
  }

  getRolesForUser(conferenceId: number, userId: number): Observable<UserRoleDto>{
    return this.httpClient.get<UserRoleDto>(this.url + "user-roles/" + conferenceId + "/" + userId);
  }

  getInvitationsForUser(conferenceId: number, userId: number): Observable<Invitation>{
    return this.httpClient.get<Invitation>(this.url + "/admin/get-chair-invitations/" + conferenceId + "/" + userId);
  }

  sendChairInvitation(invitation: Invitation): Observable<Invitation> {
    return this.httpClient.post<Invitation>(this.url + "/admin/invite-chair", invitation);
  }

  cancelChairInvitation(conferenceId: number, receiverId: number): Observable<void> {
    return this.httpClient.delete<void>(this.url + "/admin/cancel-invite-chair/" + conferenceId + "/" + receiverId);
  }
}
