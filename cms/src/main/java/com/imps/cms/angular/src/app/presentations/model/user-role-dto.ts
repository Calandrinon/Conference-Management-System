import {UserType} from "./user-type";

export interface UserRoleDto{
  id: number
  conferenceId: number
  userId: number
  userType: UserType
}
