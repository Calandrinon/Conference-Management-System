import { Component, OnInit } from '@angular/core';
import {Conference} from "../presentations/model/conference";
import {UserDto} from "../authentication/model/UserDto";
import {PcMemberService} from "./service/pc-member.service";
import {Proposal} from "../presentations/model/proposal";
import {Paper} from "../presentations/model/paper";
import {NgForm} from "@angular/forms";
import {Bid} from "../presentations/model/bid";
import {Review} from "../presentations/model/review";
import {Comment} from "../presentations/model/comment";
import {Observable} from "rxjs";

@Component({
  selector: 'app-pc-member-page',
  templateUrl: './pc-member-page.component.html',
  styleUrls: ['./pc-member-page.component.css']
})
export class PcMemberPageComponent implements OnInit {
  public conference: Conference;
  public loggedUser: UserDto
  public bidProposalShow: boolean = false
  public reviewProposalShow: boolean = false

  public today: Date;

  public proposals: Proposal[]
  public currentProposal: Proposal
  public papers: {[id: number]: Paper} = {}
  public bids: {[id: number]: Bid} = {}

  public reviews: Review[]
  public proposalsToReview: {[id: number]: Proposal} = {}
  public currentReview: Review
  public reviewSubmitted: {[id: number]: boolean} = {}
  public submittedReviewsForProposal: {[id: number]: Review[]} = {}

  constructor(private pcMemberService: PcMemberService) { }

  ngOnInit(): void {
    this.today = new Date();
    this.conference = JSON.parse(sessionStorage.getItem('conference'))
    console.log(this.conference)
    this.loggedUser = JSON.parse(localStorage.getItem('current-user'))
    this.getProposals();
    this.getReviews();
  }

  checkIfDeadlinePassed(deadline: Date): boolean {
    return this.today > deadline;

  }

  getSubmittedReviewsForProposal(proposal: Proposal): void{
    this.pcMemberService.getSubmittedReviewsForProposal(proposal.id).subscribe(
      (response:Review[]) => {
        console.log(response)
        this.submittedReviewsForProposal[proposal.id] = response;
      }
    )
  }

  getProposals(): void{
    this.pcMemberService.getProposals(this.conference.id).subscribe(
      (response: Proposal[]) => {
        console.log(response)
        this.proposals = response
        for(let i = 0; i < this.proposals.length; i++){
          this.getPaperForProposal(this.proposals[i])
          this.getBidForProposal(this.proposals[i])
        }
      }
    )
  }

  showBidProposal() {
    this.reviewProposalShow = false
    this.bidProposalShow = !this.bidProposalShow
  }

  showReviewProposal() {
    this.bidProposalShow = false
    this.reviewProposalShow = !this.reviewProposalShow
  }

  private getPaperForProposal(proposal: Proposal) {
    this.pcMemberService.getPaperById(proposal.paperId).subscribe(
      (response: Paper) => {
        console.log(response)
        this.papers[response.id] = response
      }
    )
  }

  setCurrentProposal(proposal: Proposal): void {
    this.currentProposal = proposal;
  }

  placeBid(placeBidForm: NgForm) {
    let bid: Bid = {
      id: null,
      proposalId: this.currentProposal.id,
      userId: this.loggedUser.id,
      bidStatus: placeBidForm.value.status
    }
    console.log(bid)
    this.pcMemberService.placeBid(bid).subscribe(
      (response: Bid) => {
        console.log(response)
        this.getProposals();
      }
    )
  }

  private getBidForProposal(proposal: Proposal): void {
    this.pcMemberService.getBidByProposalIdAndUserId(proposal.id, this.loggedUser.id).subscribe(
      (response: Bid) => {
        console.log(response)
        this.bids[proposal.id] = response
      }
    )
  }

  private getReviews(): void{
    this.pcMemberService.getReviewsForUser(this.loggedUser.id, this.conference.id).subscribe(
      (response: Review[]) => {
        console.log("REVIEWS:")
        console.log(response)
        this.reviews = response;
        for(let i = 0; i < this.reviews.length; i++){
          this.getProposalForReview(this.reviews[i])
          this.getSubmittedReview(this.reviews[i])
        }
      }
    )
  }

  private getProposalForReview(review: Review) {
    this.pcMemberService.getProposalForReview(review.id).subscribe(
      (response: Proposal) => {
        this.proposalsToReview[review.id] = response
        console.log("Proposal for review")
        console.log(this.proposalsToReview)
        this.getSubmittedReviewsForProposal(response);
      }
    )
  }

  setCurrentReviewAndProposal(review: Review, proposal: Proposal) {
    this.currentReview = review
    this.currentProposal = proposal
  }

  reviewProposal(reviewForm: NgForm) {
    let review = this.currentReview
    review.score = reviewForm.value.score
    review.notes = reviewForm.value.notes
    console.log(review)
    this.pcMemberService.reviewProposal(review).subscribe(
      (response:Review) => {
        console.log(response)
        this.getReviews()
      }
    )
  }

  private getSubmittedReview(review: Review) {
    this.pcMemberService.getSubmittedReview(review.id).subscribe(
      (response: boolean) => {
        console.log(response)
        this.reviewSubmitted[review.id] = response
      }
    )
  }

  comment(addComment: NgForm, proposal: Proposal) {
    let comment: Comment = {
      id: 0,
      ...addComment.value,
      proposalId: proposal.id,
      userId: this.loggedUser.id,
      userName: this.loggedUser.fullName
    }
    console.log(comment)

    this.pcMemberService.postComment(comment).subscribe(
      (response: Comment) => {
        console.log(response)
        this.getReviews()
      }
    )
  }
}
