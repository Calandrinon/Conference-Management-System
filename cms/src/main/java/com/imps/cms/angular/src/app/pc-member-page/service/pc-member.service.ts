import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Proposal} from "../../presentations/model/proposal";
import {Paper} from "../../presentations/model/paper";
import {Bid} from "../../presentations/model/bid";
import {Review} from "../../presentations/model/review";

@Injectable({
  providedIn: 'root'
})
export class PcMemberService {
  private url = "http://localhost:8080/api/";
  constructor(private httpClient: HttpClient) { }

  public getProposals(conferenceId: number): Observable<Proposal[]>{
    return this.httpClient.get<Proposal[]>(this.url + "pc-member/get-proposals/" + conferenceId);
  }

  public getPaperById(paperId: number): Observable<Paper> {
    return this.httpClient.get<Paper>(this.url + "pc-member/get-paper/" + paperId)
  }

  placeBid(bid: Bid): Observable<Bid>{
    return this.httpClient.post<Bid>(this.url + "pc-member/place-bid", bid)
  }

  getBidByProposalIdAndUserId(proposalId: number, userId: number): Observable<Bid> {
    return this.httpClient.get<Bid>(this.url + "pc-member/get-bid/" + proposalId + "/" + userId)
  }

  getReviewsForUser(userId: number, conferenceId: number): Observable<Review[]> {
    return this.httpClient.get<Review[]>(this.url + "pc-member/get-reviews-for-user/" + userId + "/" + conferenceId)
  }

  getProposalForReview(reviewId: number): Observable<Proposal> {
    return this.httpClient.get<Proposal>(this.url + "pc-member/get-proposal-for-review/" + reviewId)
  }

  reviewProposal(review: Review): Observable<Review> {
    return this.httpClient.put<Review>(this.url + "pc-member/review-proposal", review)
  }

  getSubmittedReview(reviewId: number): Observable<boolean> {
    return this.httpClient.get<boolean>(this.url + "pc-member/get-submitted-review/" + reviewId)
  }
}
