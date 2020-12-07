import { Component, OnInit, Input, SimpleChange, SimpleChanges } from '@angular/core';
import { Project } from '../../../models/project.model';
import { ProjectService } from '../../../services/project.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-project-list',
    templateUrl: './project-list.component.html',
    styleUrls: ['./project-list.component.css']
})
export class ProjectListComponent implements OnInit {
    allprojects: Project[] = [];
    projects: Project[] = [];

    constructor(
        private projectService: ProjectService,
        protected router: Router
    ) {
    }


    ngOnInit(): void {
        this.updateProjects();
    }

    updateProjects(): void {
        this.projectService.getProjects().subscribe(
            result => {
                this.projects = result.map(x => Project.fromJSON(x));
                this.projects.forEach(val => this.allprojects.push(Project.assign(val)));
            }
        );
    }

    notifyMe(exec: string, value?: string): void {
        if (exec === 'search') {
            this.projects = [];
            this.projects = this.allprojects.filter(p => p.getName().includes(value));
        }
        else if (exec === 'update') {
            this.updateProjects();
        }
    }
}
