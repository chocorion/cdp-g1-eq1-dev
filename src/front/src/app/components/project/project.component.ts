import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { Project } from 'src/app/models/project.model';
import { SidebarState } from 'src/app/models/sidebar-state';
import { SidebarService } from 'src/app/services/sidebar.service';
import { ProjectService } from '../../services/project.service';
import { ProjectListComponent } from './project-list/project-list.component';

@Component({
    selector: 'app-project',
    templateUrl: './project.component.html',
    styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {

    @ViewChild('child')
    private child: ProjectListComponent;
    submit: string;
    searchForm: any;
    private formBuilder: FormBuilder = new FormBuilder();
    newProjectForm: any;
    private newProject: Project;

    constructor(private projectService: ProjectService,
                private router: Router,
                private sidebarService: SidebarService) {
        this.searchForm = this.formBuilder.group({
            search: ''
        });
        this.newProjectForm = this.formBuilder.group({
            name: '',
            description: ''
        });
    }

    ngOnInit(): void {
        this.sidebarService.changeState(SidebarState.disabled);
        this.projectService.clearCurrentProject();
    }

    onSubmitSearch(data): void {
        if (data.search !== '') {
            this.submit = data.search;
            this.child.notifyMe('search', this.submit);
        }
    }

    refresh(): void {
        this.router.routeReuseStrategy.shouldReuseRoute = () => false;
        this.router.onSameUrlNavigation = 'reload';
        this.updateChild();
    }

    onSubmitNewProject(data): void {
        this.newProject = new Project(data.name, data.description);
        this.projectService.postProject(this.newProject).subscribe(
            () => this.refresh());
    }

    updateChild(): void {
        this.child.notifyMe('update');
    }

}
