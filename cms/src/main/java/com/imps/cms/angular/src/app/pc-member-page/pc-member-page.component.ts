import { Component, OnInit } from '@angular/core';
import {Conference} from "../presentations/model/conference";
import {UserDto} from "../authentication/model/UserDto";
import {PcMemberService} from "./service/pc-member.service";
import {Proposal} from "../presentations/model/proposal";

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
  public proposals: Proposal[]
  constructor(private pcMemberService: PcMemberService) { }

  ngOnInit(): void {
    this.conference = JSON.parse(sessionStorage.getItem('conference'))
    console.log(this.conference)
    this.loggedUser = JSON.parse(localStorage.getItem('current-user'))
  }

  showBidProposal() {
    this.reviewProposalShow = false
    this.bidProposalShow = !this.bidProposalShow
  }

  showReviewProposal() {
    this.bidProposalShow = false
    this.reviewProposalShow = !this.reviewProposalShow
  }
}
