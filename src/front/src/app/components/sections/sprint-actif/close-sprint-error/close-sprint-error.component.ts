import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-close-sprint-error',
  templateUrl: './close-sprint-error.component.html',
  styleUrls: ['./close-sprint-error.component.css']
})
export class CloseSprintErrorComponent implements OnInit {

  @Output() closeSprint = new EventEmitter<any>();
  @ViewChild('content')
  private content: CloseSprintErrorComponent;


  constructor(private modalService: NgbModal
  ) { }

  ngOnInit(): void {
  }

  open(): void {
    this.modalService.open(this.content, { ariaLabelledBy: 'modal-basic-title' }).result.then((result) => {
      if (result === 'Save click') {
        this.closeSprint.emit();
      }
    });
  }
}