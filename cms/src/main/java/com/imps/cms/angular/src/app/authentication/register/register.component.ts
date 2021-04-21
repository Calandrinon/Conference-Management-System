import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(private formBuilder: FormBuilder) { }

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

  sendEmail(): void {

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
      console.log("HEY")
    }
    else
    {
      this.invalidInput = true;
    }
  }

}
