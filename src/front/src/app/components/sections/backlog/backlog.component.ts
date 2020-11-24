import {Component, OnDestroy, OnInit} from '@angular/core';
import {ProjectService} from '../../../services/project.service';
import {Sprint} from '../../../models/sprint.model';
import {SprintService} from '../../../services/sprint.service';
import {Subscription} from 'rxjs';


@Component({
    selector: 'app-backlog',
    templateUrl: './backlog.component.html',
    styleUrls: ['./backlog.component.css']
})
export class BacklogComponent implements OnInit, OnDestroy {
    subscription: Subscription;
    sprints: Sprint[] = [];

    constructor(
        private projectService: ProjectService,
        private sprintService: SprintService
    ) {}

    ngOnInit(): void {
        this.subscription = this.sprintService.subject.subscribe(
            sprintList => {
                console.log('In backlog init : ');
                console.log('Receive sprintlist : ');
                console.log(sprintList);
                this.sprints = sprintList;
            }
        );

        this.sprintService.getAllForProject(this.projectService.currentProject.getId());
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
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
