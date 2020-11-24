import {Component, OnInit} from '@angular/core';
import {ProjectService} from '../../../services/project.service';
import {Sprint} from '../../../models/sprint.model';
import {SprintService} from '../../../services/sprint.service';
import {FormBuilder, FormGroup} from '@angular/forms';


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
    ) {}

    ngOnInit(): void {
        this.sprintService.getAllForProject(this.projectService.currentProject.getId()).subscribe(
            sprints => {
                    this.sprints = [];
                    sprints.forEach(sprint => this.sprints.push(Sprint.fromJSON(sprint)));
            }
        );
    }

    sprintConnectedTo(sprint: Sprint): string[] {
        const result = [];
        const id = sprint.getId();

        this.sprints.forEach(s => {
            if (s.getId() !== id) {
                result.push('sprint' + s.getId());
            }
        });

        result.push('unplanned-container');

        return result;
    }

    unplannedConnectedTo(): string[] {
        return [...this.sprints].map(s => 'sprint' + s.getId());
    }
}
