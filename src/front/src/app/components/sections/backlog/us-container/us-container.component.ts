import {Component, Input, OnInit} from '@angular/core';
import {Sprint} from '../../../../models/sprint.model';
import {SprintService} from '../../../../services/sprint.service';
import {ProjectService} from '../../../../services/project.service';
import {UsService} from '../../../../services/us.service';
import {Us} from '../../../../models/us.model';

@Component({
    selector: 'app-us-container',
    templateUrl: './us-container.component.html',
    styleUrls: ['./us-container.component.css']
})
export class UsContainerComponent implements OnInit {
    @Input() sprint: Sprint;
    public us: Us[] = [];

    constructor(
        private usService: UsService,
        private projectService: ProjectService,
        private sprintService: SprintService
    ) {
    }

    ngOnInit(): void {
        this.sprintService.getUs(this.projectService.currentProject.getId(), this.sprint.getId()).subscribe(
            usList => {
                this.us = [];
                usList.forEach(us => this.us.push(Us.fromJSON(us)));
            }
        );
    }

}
