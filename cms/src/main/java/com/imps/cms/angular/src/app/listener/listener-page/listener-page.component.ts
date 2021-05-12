import { Component, OnInit } from '@angular/core';
import {UserDto} from "../../authentication/model/UserDto";

@Component({
  selector: 'app-listener-page',
  templateUrl: './listener-page.component.html',
  styleUrls: ['./listener-page.component.css']
})
export class ListenerPageComponent implements OnInit {

  listenerName = "";
  conferenceName = "";
  reservationFee = "";
  localStorage;

  constructor() { }

  ngOnInit(): void {
    this.listenerName = JSON.parse(localStorage.getItem('current-user')).fullName;

    this.localStorage = localStorage;
    if (localStorage.getItem('conference-name-selected') != null)
    {
      this.conferenceName = localStorage.getItem('conference-name-selected')
    }


  }

}
