import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  public id: number

  constructor() { }

  ngOnInit(): void {
    let user = localStorage.getItem('current-user');
    if(user !== 'none')
      this.id = JSON.parse(user).id;
    else this.id = -1
  }

}
