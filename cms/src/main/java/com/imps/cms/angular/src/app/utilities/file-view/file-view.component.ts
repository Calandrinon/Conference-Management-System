import {Component, Input, OnInit} from '@angular/core';
import {FileService} from "../shared/file-service";

@Component({
  selector: 'app-file-view',
  templateUrl: './file-view.component.html',
  styleUrls: ['./file-view.component.css']
})
export class FileViewComponent implements OnInit {

  @Input()
  fileId : number;
  // cand folosesti componenta adauga asta : <app-file-view [fileId]="var din componenta parinte"></app-file-view>
  // ar trebui ca view-ul sa se schimbe cand fileId-ul parintelui se schimba

  text : string = "";

  constructor(private fileService : FileService) { }

  ngOnInit(): void {
    this.fileService.getFileContent(2)                        // pune fileId in loc de 2-ul hardcodat
      .subscribe(result => {                                      // result : Blob
        new Response(result).text().then(txt => this.text = txt); // then : Promise a -> (a -> b)
      });
  }

  dis(): void {
    console.log(this.text);
  }

}
