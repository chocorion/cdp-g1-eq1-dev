import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Project } from 'src/app/models/project.model';
import { FormBuilder } from '@angular/forms';
import { ProjectService } from 'src/app/services/project.service';
import { Router } from '@angular/router';
import { TaskService } from 'src/app/services/task.service';
import { Task } from 'src/app/models/task.model';


@Component({
    selector: 'app-project-item',
    templateUrl: './project-item.component.html',
    styleUrls: ['./project-item.component.css']
})
export class ProjectItemComponent implements OnInit {
    @Input() currentProject: Project;
    @Output() update = new EventEmitter<any>();

    formP: any;
    private formBuilder: FormBuilder = new FormBuilder();

    percent: number;


    constructor(
        private projectService: ProjectService,
        private taskService: TaskService,
        private router: Router
    ) {
        this.formP = this.formBuilder.group({
            name: '',
            description: ''
        });
    }


    deleteProject(): void {
        this.projectService.deleteProject(this.currentProject).subscribe(
            () => this.update.emit());
    }

    ngOnInit(): void {
        this.taskService.getAllForProject(this.currentProject.getId());

        this.taskService.subject.subscribe(
            result => {
                this.percent = (result.filter(x => x.status === 'DONE').length / result.length) * 100;
            }
        );

        this.formP.get('name').setValue(this.currentProject.getName());
        this.formP.get('description').setValue(this.currentProject.getDescription());
    }



    onSubmit(data): void {
        this.currentProject.setName(data.name);
        this.currentProject.setDescription(data.description);
        this.projectService.updateProject(this.currentProject).subscribe(
            () => this.update.emit());
    }

    onClick(): void {
        this.router.navigate(['project/' + this.currentProject.getId() + '/backlog']);
        // TODO Remove this quickfix
        this.projectService.setCurrentProject(this.currentProject);
    }

}
