import {Component, OnInit} from '@angular/core';
import {ProjectService} from '../../../services/project.service';
import {Sprint} from '../../../models/sprint.model';
import {SprintService} from '../../../services/sprint.service';


@Component({
    selector: 'app-backlog',
    templateUrl: './backlog.component.html',
    styleUrls: ['./backlog.component.css']
})
export class BacklogComponent implements OnInit {
    sprints: Sprint[] = [];

    constructor(
        private projectService: ProjectService,
        private sprintService: SprintService
    ) {
    }

    ngOnInit(): void {
        this.sprintService.getAllForProject(this.projectService.currentProject.getId()).subscribe(
            sprints => {
                    this.sprints = [];
                    sprints.forEach(sprint => this.sprints.push(Sprint.fromJSON(sprint)));
            }
        );
    }

    sprintConnectedTo(): string[] {
        const result = [];
        this.sprints.forEach(s => result.push(+s.getId()));

        return result;
    }
}
