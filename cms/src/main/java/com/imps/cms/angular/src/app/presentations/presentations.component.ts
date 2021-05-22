import { Component, OnInit } from '@angular/core';
import {Conference} from "./model/conference";
import {ConferenceService} from "./service/conference.service";
import {UserDto} from "../authentication/model/UserDto";
import {UserRoleDto} from "./model/user-role-dto";
import {NgForm} from "@angular/forms";
import {Router} from "@angular/router";


@Component({
  selector: 'app-presentations',
  templateUrl: './presentations.component.html',
  styleUrls: ['./presentations.component.css']
})
export class PresentationsComponent implements OnInit {
  conferences: Conference[];
  public rolesDictionary: {[id: number] : UserRoleDto}={}
  toggleDictionary: {[id: number] : boolean} = {}
  roles: UserRoleDto[];
  user: UserDto;
  currentConference: Conference;

  constructor(private conferenceService: ConferenceService, private router: Router) { }

  ngOnInit(): void {
    let user = localStorage.getItem("current-user");
    if(user !== 'none'){
      this.user = JSON.parse(user);
    }
    else this.user = null;
    this.getConferences();
  }

  getConferences(): void {
    this.conferenceService.getConferences().subscribe(conferences => {
      console.log("The requested conferences: ", conferences);
      this.conferences = conferences;
      for(let i = 0; i < this.conferences.length; i++){
        this.toggleDictionary[conferences[i].id] = false;
        this.getUserRoles(conferences[i].id);
      }
    });
  }

  getUserRoles(conferenceId: number): void {

    if(this.user !== null){
      this.conferenceService.getRolesForCurrentUserForConference(conferenceId, this.user.id).subscribe(
        (response: UserRoleDto) => {this.rolesDictionary[conferenceId] = response;
        console.log(this.rolesDictionary)}
      );
    }
    else this.rolesDictionary[conferenceId] = null
  }

  setConference(conference: Conference) {
    this.currentConference = conference;
    sessionStorage.setItem("conference", JSON.stringify(conference))
  }

  addAuthorRole(conferenceId: number) {
    if(this.user !== null){
      this.conferenceService.addAuthorRole(conferenceId, this.user.id).subscribe(
        (response: UserRoleDto) => {
          console.log(response);
          this.rolesDictionary[conferenceId] = response
        }
      )
    }
  }

  addListenerRole(conferenceId: number) {
    if(this.user !== null){
      this.conferenceService.addListenerRole(conferenceId, this.user.id).subscribe(
        (response: UserRoleDto) => {
          console.log(response);
          this.rolesDictionary[conferenceId] = response
        }
      )
    }
  }

  addPcMemberRole(conferenceId: number, pcMemberForm: NgForm) {
    if(this.user !== null){
      let token = pcMemberForm.value.token;
      this.conferenceService.addPcMemberRole(conferenceId, this.user.id, token).subscribe(
        (response: UserRoleDto) => {
          console.log(response);
          this.rolesDictionary[conferenceId] = response
        }
      )
    }
  }

  addChairRole(conferenceId: number, chairForm: NgForm) {
    if(this.user !== null){
      let token = chairForm.value.token;
      this.conferenceService.addChairRole(conferenceId, this.user.id, token).subscribe(
        (response: UserRoleDto) => {
          console.log(response);
          this.rolesDictionary[conferenceId] = response
        }
      )
    }
  }

  goToAuthorPage(conference: Conference) {
    sessionStorage.setItem("conference", JSON.stringify(conference))
    this.router.navigate(['/author'])
  }

  goToListenerPage(conference: Conference) {
    sessionStorage.setItem("conference", JSON.stringify(conference))
    this.router.navigate(['/listener'])
  }

  goToPcMemberPage(conference: Conference) {
    sessionStorage.setItem("conference", JSON.stringify(conference))
    this.router.navigate(['/pc-member-page'])
  }

  goToChairPage(conference: Conference) {
    sessionStorage.setItem("conference", JSON.stringify(conference))
    this.router.navigate(['/chair-page'])
  }

  setToggle(conferenceId: number) {
    this.toggleDictionary[conferenceId] = !this.toggleDictionary[conferenceId]
  }

  addConference(addConferenceForm: NgForm) {
    let conference: Conference = {id: 0, ...addConferenceForm.value}
    console.log(conference);
    this.conferenceService.addConference(conference).subscribe(
      (response: Conference) => console.log(response)
    );
    this.getConferences();
  }

  goToAdminPage(conference: Conference) {
    sessionStorage.setItem("conference", JSON.stringify(conference))
    this.router.navigate(['/admin-page'])
  }
}
