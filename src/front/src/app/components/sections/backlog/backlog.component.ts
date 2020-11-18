import {Component, OnInit} from '@angular/core';
import {ProjectService} from '../../../services/project.service';
import {Project} from '../../../models/project.model';

@Component({
    selector: 'app-backlog',
    templateUrl: './backlog.component.html',
    styleUrls: ['./backlog.component.css']
})
export class BacklogComponent implements OnInit {
    projects: Project[] = [];

    constructor(private projectService: ProjectService) {
    }

    ngOnInit(): void {
        // https://stackoverflow.com/questions/51763745/angular-6-error-typeerror-is-not-a-function-but-it-is
        this.projectService.getProjects().subscribe(
            // @ts-ignore
            result => this.projects = result.map(x => new Project(x.id, x.name, x.description))
        );
    }
}
