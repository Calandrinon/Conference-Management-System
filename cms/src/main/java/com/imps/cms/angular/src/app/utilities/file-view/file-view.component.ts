import {Component, Input, OnInit} from '@angular/core';
import {FileService} from "../shared/file-service";

@Component({
  selector: 'app-file-view',
  templateUrl: './file-view.component.html',
  styleUrls: ['./file-view.component.css']
})
export class FileViewComponent implements OnInit {

  @Input() fileId : number = null;
  // cand folosesti componenta adauga asta : <app-file-view [fileId]="var din componenta parinte"></app-file-view>
  // ar trebui ca view-ul sa se schimbe cand fileId-ul parintelui se schimba

  text : string = "";
  public showF: boolean = false
  constructor(private fileService : FileService) { }


  ngOnInit(): void {
    console.log(this.fileId)
    this.fileService.getFileContent(this.fileId)                        // pune fileId in loc de 2-ul hardcodat
      .subscribe(result => {                                      // result : Blob
        new Response(result).text().then(txt => this.text = txt); // then : Promise a -> (a -> b)
      });
  }

  show(): void {
    this.showF = !this.showF
  }

}
