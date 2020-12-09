import {Component, Input, OnInit} from '@angular/core';
import {Us} from '../../../../models/us.model';
import {UsService} from '../../../../services/us.service';
import {ProjectService} from '../../../../services/project.service';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import {Subscription} from 'rxjs';

@Component({
    selector: 'app-unplanned',
    templateUrl: './unplanned.component.html',
    styleUrls: ['./unplanned.component.css']
})
export class UnplannedComponent implements OnInit {
    @Input() connectedTo;
    usSubscription: Subscription;

    userStories: Us[] = [];

    constructor(
        private usService: UsService,
        private projectService: ProjectService
    ) {
    }

    ngOnInit(): void {
        this.usSubscription = this.usService.getSubject(this.projectService.currentProject.getId()).subscribe(
            usList => {
                this.userStories = usList.filter(us => us.getSprint() == null);
            }
        );

        this.usService.getAllForProject(this.projectService.currentProject.getId());
    }

    drop(event: CdkDragDrop<Us[], any>): void {
        if (event.previousContainer === event.container) {
            moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
        } else {
            this.updateUsSprint(
                event.previousContainer.data[event.previousIndex]
            );

            transferArrayItem(event.previousContainer.data,
                event.container.data,
                event.previousIndex,
                event.currentIndex);
        }
    }

    updateUsSprint(us: Us): void {
        us.setSprint(null);
        this.usService.update(this.projectService.currentProject.getId(), us).subscribe();
    }
}
