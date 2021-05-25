import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FileService} from "../shared/file-service";
import {NgForm} from "@angular/forms";
import {Section} from "../../model/section-model";
import {SectionService} from "../../other-services/section-service";


@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css']
})
export class FileUploadComponent implements OnInit {

  fileToUpload : File = null;
  sections: Section[] = [];
  selected: Section = null;
  @Output() fileIdEvent : EventEmitter<number> = new EventEmitter<number>();

  constructor(private fileService : FileService, private sectionService : SectionService) { }

  ngOnInit(): void {
    this.sectionService.getSections().subscribe(result => {
      console.log(result);
      console.log(" = result");
      this.sections = result;
      console.log("sections = ", this.sections);
    });
  }


  // handleFileInput(files: FileList) {
  //   this.fileToUpload = files.item(0);
  // }

  uploadFile(form : NgForm) {
    const formData = new FormData();
    formData.append('file', this.fileToUpload);

    console.log((this.fileToUpload as File));
    console.log("********************************************************");

    this.fileService.postFile({
      data: formData
      , title: form.value.title
      , keywords: form.value.keywords
      , authorId: 3// JSON.parse(localStorage.getItem('current-user')).id
      , sectionId: 1
      , subject: form.value.subject
      , topics: form.value.topics
    }).subscribe(result => {
      this.fileIdEvent.emit(result);
      form.resetForm();});
  }


  handleFileInput(event) {
    this.fileToUpload = (event.target as HTMLInputElement).files.item(0);
    console.log(this.fileToUpload);
  }

  onSubmit(form: NgForm) {
    this.uploadFile(form);
  }

  set(event: any) {
    console.log(event);
  }

  select(section) {
    this.selected = section;
    console.log(this.selected);
  }

  print() {
    console.log((this.selected as Section).name);
  }
}
