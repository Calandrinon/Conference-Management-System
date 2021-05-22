import {Component, OnInit} from '@angular/core';
import {AdminService} from "./service/admin.service";
import {Conference} from "../presentations/model/conference";
import {UserDto} from "../authentication/model/UserDto";
import {UserRoleDto} from "../presentations/model/user-role-dto";
import {Invitation} from "../presentations/model/invitation";
import {UserType} from "../presentations/model/user-type";

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent implements OnInit {
  public loggedUser: UserDto
  public users: UserDto[]
  public conference: Conference;
  public userRoles: {[id: number]: UserRoleDto} = {}
  public invitations: {[id: number]: Invitation} = {}
  constructor(private adminService: AdminService) { }

  ngOnInit(): void {
    this.conference = JSON.parse(sessionStorage.getItem('conference'))
    console.log(this.conference)
    this.loggedUser = JSON.parse(localStorage.getItem('user'))
    this.getUsers();
  }

  getRolesForUser(userId: number): void{
    this.adminService.getRolesForUser(this.conference.id, userId).subscribe(
      (response: UserRoleDto) => {
        console.log(response)
        this.userRoles[userId] = response;
      }
    )
  }

  getInvitationsForUser(userId: number): void{
    this.adminService.getInvitationsForUser(this.conference.id, userId).subscribe(
      (response: Invitation) => {
        console.log(response)
        this.invitations[userId] = response
      }
    )
  }

  getUsers(): void{
    this.adminService.getUsers().subscribe(
      (response: UserDto[]) => {
        console.log(response);
        this.users = response;
        for(let user of this.users){
          this.getRolesForUser(user.id)
        }
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
    this.adminService.sendChairInvitation(invitation).subscribe(
      (response: Invitation) => {
        console.log(response)
      }
    )
    this.getRolesForUser(receiverId)
  }
}
