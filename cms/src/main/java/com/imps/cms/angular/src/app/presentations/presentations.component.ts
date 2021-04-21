import { Component, OnInit } from '@angular/core';
import {Conference} from "./model/conference";
import {ConferenceService} from "./service/conference.service";

@Component({
  selector: 'app-presentations',
  templateUrl: './presentations.component.html',
  styleUrls: ['./presentations.component.css']
})
export class PresentationsComponent implements OnInit {
  conferences: Conference[];

  constructor(private conferenceService: ConferenceService) { }

  ngOnInit(): void {
    this.getConferences();
  }

  getConferences(): void {
    this.conferenceService.getConferences().subscribe(conferences => {
      console.log("The requested conferences: ", conferences);
    });
  }
}
