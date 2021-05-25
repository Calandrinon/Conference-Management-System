import {ReviewStatus} from "./reviewStatus";

export interface Review{
  id: number
  userId: number
  proposalId: number
  notes: string
  score: number
  reviewStatus: ReviewStatus
}
