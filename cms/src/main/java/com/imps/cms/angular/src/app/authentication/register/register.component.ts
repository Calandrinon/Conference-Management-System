import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {RegisterService} from "../service/register.service";
import {UserDto} from "../model/UserDto";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(private formBuilder: FormBuilder, private registerService: RegisterService,
              private router: Router) { }

  registerForm = new FormGroup({
    emailInput: new FormControl('', [
      Validators.required,
      Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
    fullName: new FormControl('', [
      Validators.required,
      Validators.pattern(".{1,}")]),
    firstPasswordInput: new FormControl('', [
      Validators.required,
      Validators.pattern(".{8,}")]),
    secondPasswordInput: new FormControl('', [
      Validators.required,
      Validators.pattern(".{8,}")])
  })

  test = ""
  invalidInput = false;
  successfulRegister = "pending";

  get emailInput(){
    return this.registerForm.get('emailInput')
  }

  get firstPassword(){
    return this.registerForm.get('firstPasswordInput')
  }

  get secondPassword(){
    return this.registerForm.get('secondPasswordInput')
  }

  get fullName() {
    return this.registerForm.get('fullName')
  }

  ngOnInit(): void {
  }


  delay(ms: number) {
    return new Promise( resolve => setTimeout(resolve, ms) );
  }

  // [A-Za-z0-9._%-]+@[A-Za-z0-9._%-]+\\.[a-z]{2,3}
  // /.{23,}/
  attemptRegistration(): void {
    if (this.firstPassword.valid &&
      this.secondPassword.valid &&
      this.emailInput.valid &&
      this.fullName.valid &&
      this.firstPassword.value == this.secondPassword.value)
    {
      console.log("Successful register!")
      this.invalidInput = false;
      let userDto = new UserDto(this.emailInput.value, this.fullName.value, this.firstPassword.value)
      let result = this.registerService.registerAccount(userDto);
      result.subscribe(result => {
        // console.log("AuthComponent: response from the server -> ", result);

        if (result) {
          console.log("all good!")
          this.successfulRegister = "success";

          (async () => {
            // Do something before delay
            console.log('before delay')

            await this.delay(1000);

            // Do something after
            console.log('after delay')
            // this.router.navigate(['/home'])
          })();

        } else {
          console.log("all bad!")
          this.successfulRegister = "fail";
        }
      }
      )
    }
    else
    {
      this.invalidInput = true;
    }
  }

}
