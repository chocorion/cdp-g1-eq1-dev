import { Component, OnInit } from '@angular/core';
import {ProjectService} from '../services/project.service';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {

   submit: string;

  constructor(private projectService: ProjectService) { }

  ngOnInit(): void {
      this.projectService.clearCurrentProject();
  }

}
