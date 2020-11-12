import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-project-item',
  templateUrl: './project-item.component.html',
  styleUrls: ['./project-item.component.css']
})
export class ProjectItemComponent implements OnInit {
  @Input() projectName: string ;
  @Input() projectDescription: string;
  @Input() nbUS: number;
  @Input() totalUS: number;
  @Input() percent: number;
  constructor() { }

  ngOnInit(): void {
  }

}
