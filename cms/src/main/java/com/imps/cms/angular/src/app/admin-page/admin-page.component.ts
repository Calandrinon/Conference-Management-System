import { Component, OnInit } from '@angular/core';
import {NgForm} from "@angular/forms";
import {AdminService} from "./service/admin.service";
import {Conference} from "../presentations/model/conference";
import {Deadline} from "../presentations/model/deadline";

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent implements OnInit {

  constructor(private adminService: AdminService) { }

  ngOnInit(): void {
  }

  addConference(addConferenceForm: NgForm) {
    let conference: Conference = {id: 0, title: addConferenceForm.value.title}
    console.log(conference);
    this.adminService.addConference(conference).subscribe(
      (response: Conference) => console.log(response)
    );
  }

  addDeadline(addDeadlineForm: NgForm) {
    let deadline: Deadline = {...addDeadlineForm.value, id: 0}
    console.log(deadline);
    this.adminService.addDeadline(deadline).subscribe(
      (response: Deadline) => console.log(response)
    )
  }
}
