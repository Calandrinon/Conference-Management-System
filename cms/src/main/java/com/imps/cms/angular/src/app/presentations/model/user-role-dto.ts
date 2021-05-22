export interface UserRoleDto{
  id: number
  conferenceId: number
  userId: number
  isChair: boolean
  isAuthor: boolean
  isPcMember: boolean
  isListener: boolean
}
