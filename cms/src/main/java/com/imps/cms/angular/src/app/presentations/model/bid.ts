import {BidStatus} from "./bid-status";

export interface Bid{
  id: number
  proposalId: number
  userId: number
  bidStatus: BidStatus
}
