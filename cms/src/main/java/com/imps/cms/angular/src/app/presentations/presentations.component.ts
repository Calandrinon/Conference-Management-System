import { Component, OnInit } from '@angular/core';
import {Conference} from "./model/conference";
import {ConferenceService} from "./service/conference.service";
import {Deadline} from "./model/deadline";
import {UserDto} from "../authentication/model/UserDto";
import {UserRoleDto} from "./model/user-role-dto";


@Component({
  selector: 'app-presentations',
  templateUrl: './presentations.component.html',
  styleUrls: ['./presentations.component.css']
})
export class PresentationsComponent implements OnInit {
  conferences: Conference[];
  public deadlinesDictionary: {[id: number] : Deadline[]}= {}
  rolesDictionary: {[id: number] : UserRoleDto[]}={}
  toggleDictionary: {[id: number] : boolean} = {}
  roles: UserRoleDto[];

  constructor(private conferenceService: ConferenceService) { }

  ngOnInit(): void {
    this.getConferences();
  }

  getConferences(): void {
    this.conferenceService.getConferences().subscribe(conferences => {
      console.log("The requested conferences: ", conferences);
      this.conferences = conferences;
      for(let i = 0; i < this.conferences.length; i++){
        this.toggleDictionary[conferences[i].id] = false;
        this.deadlinesDictionary[conferences[i].id] = [];
        this.rolesDictionary[conferences[i].id] = [];
      }
    });
  }

  getDeadlinesForConference(id: number) {
    this.getUserRoles(id);
    this.conferenceService.getDeadlinesForConferenceId(id).subscribe(deadlines =>{
      console.log("The requested deadlines: ", deadlines);
      this.deadlinesDictionary[id] = deadlines;
      this.toggleDictionary[id] = ! this.toggleDictionary[id];
    })
  }

  getUserRoles(conferenceId: number): void {
    let user: UserDto = JSON.parse(localStorage.getItem("current-user"));
    console.log(user);
    this.conferenceService.getRolesForCurrentUserForConference(conferenceId, user.id).subscribe(
      (roles: UserRoleDto[]) => this.rolesDictionary[conferenceId] = roles
    );
  }

}
