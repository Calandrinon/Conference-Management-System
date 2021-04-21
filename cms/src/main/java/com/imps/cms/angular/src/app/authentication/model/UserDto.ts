export class UserDto {
  id = -1;
  fullName: string;
  salt = 'salt';
  email: string;
  password: string;
  sectionId = -1;

  constructor(email, fullName, password) {
    this.email = email;
    this.fullName = fullName;
    this.password = password;
  }
}
