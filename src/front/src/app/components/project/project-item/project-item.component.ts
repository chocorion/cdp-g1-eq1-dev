import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import { Project } from 'src/app/models/project.model';
import { FormBuilder } from '@angular/forms';
import { ProjectService } from 'src/app/services/project.service';
import { Router } from '@angular/router';
import { TaskService } from 'src/app/services/task.service';
import {Subscription} from 'rxjs';


@Component({
    selector: 'app-project-item',
    templateUrl: './project-item.component.html',
    styleUrls: ['./project-item.component.css']
})
export class ProjectItemComponent implements OnInit, OnDestroy {
    @Input() currentProject: Project;
    @Output() update = new EventEmitter<any>();

    formP: any;
    private formBuilder: FormBuilder = new FormBuilder();

    percent: number;
    taskSubscription: Subscription;

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
        this.taskSubscription = this.taskService.getSubject(this.currentProject.getId()).subscribe(
            result => {
                this.percent = (result.filter(x => x.getStatus() === 'DONE').length / result.length) * 100;
            }
        );

        this.taskService.getAllForProject(this.currentProject.getId());

        this.formP.get('name').setValue(this.currentProject.getName());
        this.formP.get('description').setValue(this.currentProject.getDescription());
    }

    ngOnDestroy(): void {
        this.taskSubscription.unsubscribe();
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
