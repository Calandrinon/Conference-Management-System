import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.css']
})
export class NavigationBarComponent implements OnInit {
  public id: number
  constructor(private router: Router) { }

  ngOnInit(): void {
    let user = localStorage.getItem('current-user');
    if(user !== 'none')
      this.id = JSON.parse(user).id;
    else this.id = -1
  }

  logout() {
    localStorage.setItem('current-user', 'none');
    this.router.navigate(['/home']).then(p => {window.location.reload(); return true});
  }
}
