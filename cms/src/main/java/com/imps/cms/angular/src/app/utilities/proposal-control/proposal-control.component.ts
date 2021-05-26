import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FileService} from "../shared/file-service";
import {PaperWeb} from "../shared/paper-web-model";
import {Review} from "../shared/review-model";
import {ReviewService} from "../shared/review-service";
import {ProposalService} from "../shared/proposal-service";
import {UserDto} from "../../authentication/model/UserDto";
import {Conference} from "../../presentations/model/conference";

@Component({
  selector: 'app-proposal-control',
  templateUrl: './proposal-control.component.html',
  styleUrls: ['./proposal-control.component.css']
})
export class ProposalControlComponent implements OnInit {


  paperWebList : PaperWeb[] = []
  reviewList : Review[] = [];
  showUpload : boolean = false;
  submitted: boolean = false;
  user: UserDto;
  conference: Conference
  selectedPaper: PaperWeb = null;
  idToReturn : number = null;

  buttonMessages = {
    0:"Add Proposal"
    , 1:"Cancel"
  }
  show: boolean = true;


  constructor(
    private fileService : FileService
    , private reviewService : ReviewService
    , private proposalService : ProposalService
  ) { }

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem("current-user"));
    this.conference = JSON.parse(sessionStorage.getItem('conference'));
    // todo add the userid from the session
    this.fileService.getPaperWebFormat(this.user.id)
      .subscribe(
        result =>
        {
          this.paperWebList = result;
          console.log(result);
        });
  }

  showReviews(paper : PaperWeb) {
    this.reviewService.getReviewsForProposal(paper.proposalId)
      .subscribe(
        result =>
        {
          this.reviewList = result;
          console.log(result);
        });
  }

  uploadComplete($event: number) {
    document.getElementById('exampleModal').click();
    this.showUpload = false;
    this.submitted = true;
    if ($event != null) {
      this.proposalService.postProposal({
        id : null
        , paperId : $event
        , status : "PENDING"
        , commentsAllowed : false
        , comments : null
      }).subscribe(() => {
        this.ngOnInit();
      });
    }else{
      this.ngOnInit()
    }
    this.idToReturn = null;
  }

  select(paper) {
    if (this.selectedPaper == paper){
      this.selectedPaper = null;
    }else{
      this.selectedPaper = paper;
    }
  }
}
