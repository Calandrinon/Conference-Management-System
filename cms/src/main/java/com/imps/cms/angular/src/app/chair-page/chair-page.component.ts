import {Component, OnInit} from '@angular/core';
import {Conference} from "../presentations/model/conference";
import {ChairService} from "./service/chair.service";
import {UserDto} from "../authentication/model/UserDto";
import {UserRoleDto} from "../presentations/model/user-role-dto";
import {Invitation} from "../presentations/model/invitation";
import {HttpErrorResponse} from "@angular/common/http";
import {UserType} from "../presentations/model/user-type";
import {Proposal} from "../presentations/model/proposal";
import {Paper} from "../presentations/model/paper";
import {Review} from "../presentations/model/review";
import {ReviewStatus} from "../presentations/model/reviewStatus";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-chair-page',
  templateUrl: './chair-page.component.html',
  styleUrls: ['./chair-page.component.css']
})
export class ChairPageComponent implements OnInit {
  public conference: Conference;
  public loggedUser: UserDto
  public users: UserDto[]
  public userRoles: {[id: number]: UserRoleDto} = {}
  public chairInvitations: {[id: number]: Invitation} = {}
  public pcMemberInvitations: {[id: number]: Invitation} = {}

  public proposals: Proposal[]
  public papers: {[id: number]: Paper} = {}
  public acceptedUsers: {[id: number]: UserDto[]} = {}
  public rejectedUsers: {[id: number]: UserDto[]} = {}
  public ughUsers: {[id: number]: UserDto[]} = {}
  public currentUser: UserDto
  public currentProposal: Proposal
  public reviews: {[id: number]: Review[]} = {}
  public pendingForChair: {[id: number]: Review[]} = {}
  public userForReview : {[id: number]: UserDto} = {}

  public invitePanelShow: boolean = false
  public assignProposalsShow: boolean = false
  public resolveReviewsShow: boolean = false
  constructor(private chairService: ChairService) { }

  ngOnInit(): void {
    this.conference = JSON.parse(sessionStorage.getItem('conference'))
    console.log(this.conference)
    this.loggedUser = JSON.parse(localStorage.getItem('current-user'))
    this.getUsers();
    this.getProposals()
  }

  showInvitePanel() {
    this.resolveReviewsShow = false
    this.assignProposalsShow = false
    this.invitePanelShow = !this.invitePanelShow
  }

  showAssignProposalsPanel() {
    this.resolveReviewsShow = false
    this.invitePanelShow = false
    this.assignProposalsShow = !this.assignProposalsShow
  }

  showResolveReviewsPanel() {
    this.invitePanelShow = false
    this.assignProposalsShow = false
    this.resolveReviewsShow = !this.resolveReviewsShow
  }

  private getUsers() {
    this.chairService.getUsers().subscribe(
      (response: UserDto[]) => {
        console.log(response);
        this.users = response;
        for(let user of this.users){
          this.getRolesForUser(user.id)
          this.getChairInvitationsForUser(user.id)
          this.getPcMemberInvitationsForUser(user.id);
        }
      }
    )
  }

  getRolesForUser(userId: number): void{
    this.chairService.getRolesForUser(this.conference.id, userId).subscribe(
      (response: UserRoleDto) => {
        console.log(response)
        this.userRoles[userId] = response;
      }
    )
  }

  getChairInvitationsForUser(userId: number): void{
    this.chairService.getChairInvitationsForUser(this.conference.id, userId).subscribe(
      (response: Invitation) => {
        console.log(response)
        this.chairInvitations[userId] = response
      },
      (response: HttpErrorResponse) => {
        console.log(response)
        this.chairInvitations[userId] = null
      }
    )
  }
  getPcMemberInvitationsForUser(userId: number): void{
    this.chairService.getPcMemberInvitationsForUser(this.conference.id, userId).subscribe(
      (response: Invitation) => {
        console.log(response)
        this.pcMemberInvitations[userId] = response
      },
      (response: HttpErrorResponse) => {
        console.log(response)
        this.pcMemberInvitations[userId] = null
      }
    )
  }

  inviteChair(receiverId: number): void {
    let invitation: Invitation = {
      conferenceId: this.conference.id,
      receiverId: receiverId,
      senderId: this.loggedUser.id,
      userType: UserType.CHAIR,
      status: "PENDING"
    }
    this.chairService.sendChairInvitation(invitation).subscribe(
      (response: Invitation) => {
        console.log(response)
      }
    )
    this.getUsers();
  }

  cancelChairInvitation(receiverId: number): void{
    this.chairService.cancelChairInvitation(this.conference.id, receiverId).subscribe(
      (response: void) => {
        console.log(response)
      }
    )
    this.getUsers();
  }

  invitePcMember(receiverId: number): void {
    let invitation: Invitation = {
      conferenceId: this.conference.id,
      receiverId: receiverId,
      senderId: this.loggedUser.id,
      userType: UserType.PC_MEMBER,
      status: "PENDING"
    }
    this.chairService.sendPcMemberInvitation(invitation).subscribe(
      (response: Invitation) => {
        console.log(response)
      }
    )
    this.getUsers();
  }

  cancelPcMemberInvitation(receiverId: number): void{
    this.chairService.cancelPcMemberInvitation(this.conference.id, receiverId).subscribe(
      (response: void) => {
        console.log(response)
      }
    )
    this.getUsers();
  }

  private getPaperForProposal(proposal: Proposal) {
    this.chairService.getPaperById(proposal.paperId).subscribe(
      (response: Paper) => {
        console.log(response)
        this.papers[response.id] = response
      }
    )
  }

  private getProposals(): void {
    this.chairService.getProposals(this.conference.id).subscribe(
      (response:Proposal[]) => {
        console.log(response)
        this.proposals = response;
        for(let i = 0; i < this.proposals.length; i++) {
          this.getPaperForProposal(this.proposals[i])
          this.getAcceptForProposal(this.proposals[i])
          this.getRejectForProposal(this.proposals[i])
          this.getUghForProposal(this.proposals[i])
          this.getReviews(this.proposals[i])
        }
        console.log(response)
      }
    )
  }

  getAcceptForProposal(proposal: Proposal): void{
    this.chairService.getAcceptForProposal(proposal.id).subscribe(
      (response: UserDto[]) => {
        this.acceptedUsers[proposal.id] = response;
      }
    );
  }

  getRejectForProposal(proposal: Proposal): void{
    this.chairService.getRejectForProposal(proposal.id).subscribe(
      (response: UserDto[]) => {
        this.rejectedUsers[proposal.id] = response;
      }
    );
  }

  getUghForProposal(proposal: Proposal): void{
    this.chairService.getUghForProposal(proposal.id).subscribe(
      (response: UserDto[]) => {
        this.ughUsers[proposal.id] = response;
      }
    );
  }

  setCurrentProposalAndUser(proposal: Proposal, user: UserDto) {
    this.currentProposal = proposal
    this.currentUser = user
  }

  assignProposal(proposal: Proposal, user: UserDto): void {
    this.chairService.assignProposal(proposal.id, user.id).subscribe(
      (response: Review) => {
        console.log(response)
      }
    );
    this.getProposals()
  }

  private getReviews(proposal: Proposal) {
    this.chairService.getReviewsForProposal(proposal.id).subscribe(
      (response: Review[]) => {
        console.log(response)
        this.reviews[proposal.id] = response
        this.pendingForChair[proposal.id] = response.filter(review => review.reviewStatus.toString() === "PENDING_FOR_CHAIR");
      }
    )
  }

  reviewExists(user: UserDto, reviews: Review[]) {
    if(reviews === null)return false
    for(let i = 0; i < reviews.length; i++){
      if(reviews[i].userId === user.id){
        return true
      }
    }
    return false
  }


  setCurrentProposal(proposal: Proposal) {
    this.currentProposal = proposal;
  }

  manualReview(reviewProposal: NgForm) {
    let proposal: Proposal = {
      ...this.currentProposal,
      status: reviewProposal.value.status
    }
    this.chairService.manualReview(proposal).subscribe(
      (response: Proposal) => {
        console.log(response)
        this.getProposals();
      }
    )
  }

  autoReview(proposal: Proposal) {
    this.chairService.autoReview(proposal).subscribe(
      (response: Proposal) => {
        console.log(response);
        if(response.status === "CONTRADICTORY")
          document.getElementById("contradictory-button").click()
        this.getProposals();
      })
  }

  contradictoryReview(contradictoryForm: NgForm) {
    switch (contradictoryForm.value.manner){
      case "MANUAL":
        document.getElementById("manual-review-button").click()
    }
  }
}
