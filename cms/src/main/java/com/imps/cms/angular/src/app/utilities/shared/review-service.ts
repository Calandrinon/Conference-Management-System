import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Paper} from "./file-model";
import {Proposal} from "./proposal-model";
import {Review} from "./review-model";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private url = "http://localhost:8080/api/";
  constructor(private httpClient: HttpClient) { }

  getReviewsForProposal(propId: number) : Observable<Review[]> {
    return this.httpClient.get<Review[]>(
      this.url
      + "reviewsForProposal/"
      + propId);
  }
}
