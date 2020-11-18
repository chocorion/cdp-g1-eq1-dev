import { Component, OnInit, Output, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Subject } from 'rxjs';
import {ProjectService} from '../../services/project.service';
import { ProjectListComponent } from './project-list/project-list.component';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {

  @ViewChild('child')
  private child : ProjectListComponent;
  submit : string;
  form: any;
  private formBuilder: FormBuilder = new FormBuilder();

  constructor(private projectService: ProjectService) {
    this.form = this.formBuilder.group({
        search: ''
    });
}

  ngOnInit(): void {
      this.projectService.clearCurrentProject();
  }

  onSubmit(data): void {
    this.submit = data.search;
    this.child.notifyMe(this.submit);

}

}
