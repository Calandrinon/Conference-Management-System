import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  public id: number
  constructor() { }

  ngOnInit(): void {
    let user = localStorage.getItem('current-user');
    if(user !== 'none')
      this.id = JSON.parse(user).id;
    else this.id = -1
  }

}
