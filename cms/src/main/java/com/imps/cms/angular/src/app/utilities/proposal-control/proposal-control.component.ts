import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FileService} from "../shared/file-service";
import {PaperWeb} from "../shared/paper-web-model";
import {Review} from "../shared/review-model";
import {ReviewService} from "../shared/review-service";
import {Paper} from "../shared/file-model";
import {ProposalService} from "../shared/proposal-service";

@Component({
  selector: 'app-proposal-control',
  templateUrl: './proposal-control.component.html',
  styleUrls: ['./proposal-control.component.css']
})
export class ProposalControlComponent implements OnInit {

  insertingIntoFile : boolean = false;
  selectedFileId = null;
  paperWebList : PaperWeb[] = []
  reviewList : Review[] = [];
  selected : PaperWeb = null;
  showUpload : boolean = false;
  submitted: boolean = false;

  buttonMessages = {
    0:"Add Proposal"
    , 1:"Cancel"
  }
  buttonState : number = 0;


  constructor(
    private fileService : FileService
    , private reviewService : ReviewService
    , private proposalService : ProposalService
  ) { }

  ngOnInit(): void {
    // todo add the userid from the session
    this.fileService.getPaperWebFormat(3)
      .subscribe(
        result =>
        {
          this.paperWebList = result;
          console.log(result);
        });
  }

  showReviews(paper : PaperWeb) {
    this.selected = paper;
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
    this.buttonState = 0;
    this.submitted = true;
    this.proposalService.postProposal({
      id : null
      , paperId : $event
      , status : "PENDING"
    });
  }

}
