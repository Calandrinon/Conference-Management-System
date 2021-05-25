import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserDto} from "../authentication/model/UserDto";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  public id: number
  url = "http://localhost:8080/api/";
  email = "";
  userData : UserDto;
  @ViewChild('newPassword') newPassword: ElementRef;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    let user = localStorage.getItem('current-user');
    if(user !== 'none')
    {
      this.userData = JSON.parse(localStorage.getItem('current-user'));
      this.id = JSON.parse(user).id;
      this.email = JSON.parse(user).email;
    }
    else
    {
      this.id = -1
      this.email = "missing";
    }
  }

  changePassword(newPassword): void {
    this.http.get(this.url + "updateAccount/" + this.email + "/" + newPassword.value).subscribe();
    this.newPassword.nativeElement.value = "";
    console.log(this.userData.email)
  }

}
