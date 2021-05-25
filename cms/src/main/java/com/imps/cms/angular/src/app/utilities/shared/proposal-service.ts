import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Paper} from "./file-model";
import {Proposal} from "./proposal-model";


@Injectable({
  providedIn: 'root'
})
export class ProposalService {
  private url = "http://localhost:8080/api/";
  constructor(private httpClient: HttpClient) { }

  postProposal(proposal: Proposal) {
    console.log(proposal)
    return this.httpClient.post<number>(
      this.url
      + "/chair/proposal"
      , proposal);
  }
}
