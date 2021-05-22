import { Component, OnInit } from '@angular/core';
import {Conference} from "../presentations/model/conference";
import {ChairService} from "./service/chair.service";
import {UserDto} from "../authentication/model/UserDto";
import {UserRoleDto} from "../presentations/model/user-role-dto";
import {Invitation} from "../presentations/model/invitation";
import {HttpErrorResponse} from "@angular/common/http";
import {UserType} from "../presentations/model/user-type";

@Component({
  selector: 'app-chair-page',
  templateUrl: './chair-page.component.html',
  styleUrls: ['./chair-page.component.css']
})
export class ChairPageComponent implements OnInit {
  public conference: Conference;
  public loggedUser: UserDto
  public users: UserDto[]
  public userRoles: {[id: number]: UserRoleDto} = {}
  public chairInvitations: {[id: number]: Invitation} = {}
  public pcMemberInvitations: {[id: number]: Invitation} = {}
  public invitePanelShow: boolean = false
  constructor(private chairService: ChairService) { }

  ngOnInit(): void {
    this.conference = JSON.parse(sessionStorage.getItem('conference'))
    console.log(this.conference)
    this.loggedUser = JSON.parse(localStorage.getItem('current-user'))
    this.getUsers();
  }

  showInvitePanel() {
    this.invitePanelShow = !this.invitePanelShow
  }

  private getUsers() {
    this.chairService.getUsers().subscribe(
      (response: UserDto[]) => {
        console.log(response);
        this.users = response;
        for(let user of this.users){
          this.getRolesForUser(user.id)
          this.getChairInvitationsForUser(user.id)
          this.getPcMemberInvitationsForUser(user.id);
        }
      }
    )
  }

  getRolesForUser(userId: number): void{
    this.chairService.getRolesForUser(this.conference.id, userId).subscribe(
      (response: UserRoleDto) => {
        console.log(response)
        this.userRoles[userId] = response;
      }
    )
  }

  getChairInvitationsForUser(userId: number): void{
    this.chairService.getChairInvitationsForUser(this.conference.id, userId).subscribe(
      (response: Invitation) => {
        console.log(response)
        this.chairInvitations[userId] = response
      },
      (response: HttpErrorResponse) => {
        console.log(response)
        this.chairInvitations[userId] = null
      }
    )
  }
  getPcMemberInvitationsForUser(userId: number): void{
    this.chairService.getPcMemberInvitationsForUser(this.conference.id, userId).subscribe(
      (response: Invitation) => {
        console.log(response)
        this.pcMemberInvitations[userId] = response
      },
      (response: HttpErrorResponse) => {
        console.log(response)
        this.pcMemberInvitations[userId] = null
      }
    )
  }

  inviteChair(receiverId: number): void {
    let invitation: Invitation = {
      conferenceId: this.conference.id,
      receiverId: receiverId,
      senderId: this.loggedUser.id,
      userType: UserType.CHAIR,
      status: "PENDING"
    }
    this.chairService.sendChairInvitation(invitation).subscribe(
      (response: Invitation) => {
        console.log(response)
      }
    )
    this.getUsers();
  }

  cancelChairInvitation(receiverId: number): void{
    this.chairService.cancelChairInvitation(this.conference.id, receiverId).subscribe(
      (response: void) => {
        console.log(response)
      }
    )
    this.getUsers();
  }

  invitePcMember(receiverId: number): void {
    let invitation: Invitation = {
      conferenceId: this.conference.id,
      receiverId: receiverId,
      senderId: this.loggedUser.id,
      userType: UserType.PC_MEMBER,
      status: "PENDING"
    }
    this.chairService.sendPcMemberInvitation(invitation).subscribe(
      (response: Invitation) => {
        console.log(response)
      }
    )
    this.getUsers();
  }

  cancelPcMemberInvitation(receiverId: number): void{
    this.chairService.cancelPcMemberInvitation(this.conference.id, receiverId).subscribe(
      (response: void) => {
        console.log(response)
      }
    )
    this.getUsers();
  }
}
