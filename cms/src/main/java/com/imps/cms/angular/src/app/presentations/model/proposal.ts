import {Comment} from "./comment";

export interface Proposal{
  id: number
  paperId: number
  status: string
  commentsAllowed: boolean
  comments: Comment[]
}
