import {UserType} from "./user-type";

export interface Invitation{
  conferenceId: number
  senderId: number
  receiverId: number
  userType: UserType
  status: string
}
