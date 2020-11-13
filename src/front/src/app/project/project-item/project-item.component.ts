import { Component, Input, OnInit } from '@angular/core';
import { Project } from 'src/app/model/project.model';
import { FormBuilder } from '@angular/forms';
import { ProjectService } from 'src/app/services/project.service';
@Component({
  selector: 'app-project-item',
  templateUrl: './project-item.component.html',
  styleUrls: ['./project-item.component.css']
})
export class ProjectItemComponent implements OnInit {
  @Input() currentProject: Project;
  form: any;
  private formBuilder: FormBuilder = new FormBuilder();
  constructor(private projectService: ProjectService) {
    this.form = this.formBuilder.group({
      name: '',
      description: ''
    });
  }

  ngOnInit(): void {
  }

  deleteProject(): void{
    this.projectService.deleteProject(this.currentProject);
    console.log('delete incomming...');
  }

  onSubmit(data): void {
    console.log(data);
    this.currentProject.setName(data.name);
    this.currentProject.setDescription(data.description);
    this.projectService.updateProject(this.currentProject);

  }


}
