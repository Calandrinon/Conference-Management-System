import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {UserDto} from "../../authentication/model/UserDto";
import {UserRoleDto} from "../../presentations/model/user-role-dto";
import {HttpClient} from "@angular/common/http";
import {Invitation} from "../../presentations/model/invitation";
import {Proposal} from "../../presentations/model/proposal";
import {Paper} from "../../presentations/model/paper";
import {Review} from "../../presentations/model/review";
import * as url from "url";

@Injectable({
  providedIn: 'root'
})
export class ChairService {
  private url = "http://localhost:8080/api/";
  constructor(private httpClient: HttpClient) { }

  getUsers(): Observable<UserDto[]> {
    return this.httpClient.get<UserDto[]>(this.url + "users/all")
  }

  getRolesForUser(conferenceId: number, userId: number): Observable<UserRoleDto>{
    return this.httpClient.get<UserRoleDto>(this.url + "user-roles/" + conferenceId + "/" + userId);
  }

  getChairInvitationsForUser(conferenceId: number, userId: number): Observable<Invitation>{
    return this.httpClient.get<Invitation>(this.url + "/chair/get-chair-invitations/" + conferenceId + "/" + userId);
  }

  getPcMemberInvitationsForUser(conferenceId: number, userId: number): Observable<Invitation>{
    return this.httpClient.get<Invitation>(this.url + "/chair/get-pc-member-invitations/" + conferenceId + "/" + userId);
  }

  sendChairInvitation(invitation: Invitation): Observable<Invitation> {
    return this.httpClient.post<Invitation>(this.url + "/chair/invite-chair", invitation);
  }

  cancelChairInvitation(conferenceId: number, receiverId: number): Observable<void> {
    return this.httpClient.delete<void>(this.url + "/chair/cancel-invite-chair/" + conferenceId + "/" + receiverId);
  }

  sendPcMemberInvitation(invitation: Invitation): Observable<Invitation> {
    return this.httpClient.post<Invitation>(this.url + "/chair/invite-pc-member", invitation);
  }

  cancelPcMemberInvitation(conferenceId: number, receiverId: number): Observable<void> {
    return this.httpClient.delete<void>(this.url + "/chair/cancel-invite-pc-member/" + conferenceId + "/" + receiverId);
  }

  getProposals(conferenceId: number): Observable<Proposal[]> {
    return this.httpClient.get<Proposal[]>(this.url + "/chair/get-proposals/" + conferenceId)
  }

  public getPaperById(paperId: number): Observable<Paper> {
    return this.httpClient.get<Paper>(this.url + "chair/get-paper/" + paperId)
  }

  public getAcceptForProposal(proposalId: number): Observable<UserDto[]> {
    return this.httpClient.get<UserDto[]>(this.url + "chair/get-accept-users/" + proposalId)
  }

  public getRejectForProposal(proposalId: number): Observable<UserDto[]> {
    return this.httpClient.get<UserDto[]>(this.url + "chair/get-reject-users/" + proposalId)
  }

  public getUghForProposal(proposalId: number): Observable<UserDto[]> {
    return this.httpClient.get<UserDto[]>(this.url + "chair/get-ugh-users/" + proposalId)
  }

  public assignProposal(proposalId: number, userId: number): Observable<Review> {
    return this.httpClient.get<Review>(this.url + "chair/assign-proposal/" + proposalId + "/" + userId)
  }

  getReviewsForProposal(proposalId: number): Observable<Review[]> {
    return this.httpClient.get<Review[]>(this.url + "chair/get-reviews/" + proposalId)
  }

  manualReview(proposal: Proposal): Observable<Proposal> {
    return this.httpClient.post<Proposal>(this.url + "/chair/manual-review", proposal)
  }

  autoReview(proposal: Proposal): Observable<Proposal> {
    return this.httpClient.post<Proposal>(this.url + "/chair/auto-review", proposal)
  }
}
