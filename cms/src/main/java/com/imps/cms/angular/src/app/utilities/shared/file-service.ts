import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Paper} from "./file-model";
import {PaperWeb} from "./paper-web-model";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class FileService {
  private url = "http://localhost:8080/api/";
  constructor(private httpClient: HttpClient) { }

  postFile(paper: Paper) {
    console.log(paper)
    return this.httpClient.post<number>(
      this.url
        + "file/"
        + paper.title
        + "/"
        + paper.subject
        + "/"
        + paper.keywords
        + "/"
        + paper.topics
        + "/"
        + paper.authorId
        + "/"
        + paper.sectionId
        + "/"
        + paper.conferenceId
      , paper.data);
  }

  getFileContent(id: number) : any {
    return this.httpClient.get(this.url
      + "files/"
      + id,
      {responseType: 'blob'});
  }

  getPaperWebFormat(id: number) : Observable<PaperWeb[]>{
    let ooga : any = this.httpClient.get<PaperWeb[]>(this.url
      + "chair/webPapers/"
      + id
    );
    console.log(ooga);
    return ooga;
  }
}
