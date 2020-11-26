import {Component, Input, OnInit} from '@angular/core';
import {Project} from 'src/app/models/project.model';
import {FormBuilder} from '@angular/forms';
import {ProjectService} from 'src/app/services/project.service';
import {Router} from '@angular/router';

@Component({
    selector: 'app-project-item',
    templateUrl: './project-item.component.html',
    styleUrls: ['./project-item.component.css']
})
export class ProjectItemComponent implements OnInit {
    @Input() currentProject: Project;
    formP: any;
    private formBuilder: FormBuilder = new FormBuilder();

    constructor(
        private projectService: ProjectService,
        private router: Router
    ) {
    }

    refresh(): void{
        this.router.routeReuseStrategy.shouldReuseRoute = () => false;
        this.router.onSameUrlNavigation = 'reload';
    }

    deleteProject(): void{
        this.projectService.deleteProject(this.currentProject).subscribe(
            () => this.refresh());
        console.log('delete incoming...');
    }

    ngOnInit(): void {
        console.log('id ' + this.currentProject.getId() + ' ' + this.currentProject.getName());
        this.formP = this.formBuilder.group({
            name: this.currentProject.getName(),
            description: this.currentProject.getDescription()
        });
    }

    onSubmit(data): void {
        console.log('id ' + this.currentProject.getId() + ' ' + this.currentProject.getName());
        this.currentProject.setName(data.name);
        this.currentProject.setDescription(data.description);
        this.projectService.updateProject(this.currentProject).subscribe(
            () => this.refresh());
    }

    onClick(): void {
        this.router.navigate(['project/' + this.currentProject.getId() + '/backlog']);
    }
}
