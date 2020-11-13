import { Component, Input, OnInit } from '@angular/core';
import { Project } from 'src/app/model/project.model';

@Component({
  selector: 'app-project-item',
  templateUrl: './project-item.component.html',
  styleUrls: ['./project-item.component.css']
})
export class ProjectItemComponent implements OnInit {
  @Input() currentProject : Project;
  constructor() { }

  ngOnInit(): void {
  }

 

}
