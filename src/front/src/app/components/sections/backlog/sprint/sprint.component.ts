import {Component, Input, OnInit} from '@angular/core';
import {Sprint} from '../../../../models/sprint.model';
import {SprintService} from '../../../../services/sprint.service';
import {ProjectService} from '../../../../services/project.service';
import {UsService} from '../../../../services/us.service';
import {Us} from '../../../../models/us.model';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import {Router} from '@angular/router';


@Component({
    selector: 'app-us-container',
    templateUrl: './sprint.component.html',
    styleUrls: ['./sprint.component.css']
})
export class SprintComponent implements OnInit {
    @Input() sprint: Sprint;
    @Input() connectedTo: string[];

    public userStories: Us[] = [];
    public displayStartButton: boolean;

    constructor(
        private usService: UsService,
        private projectService: ProjectService,
        private sprintService: SprintService,
        private router: Router
    ) {
    }

    ngOnInit(): void {
        this.sprintService.getUs(this.projectService.currentProject.getId(), this.sprint.getId()).subscribe(
            usList => {
                this.userStories = [];
                usList.forEach(us => this.userStories.push(Us.fromJSON(us)));
            }
        );

        this.displayStartButton = false;
        this.sprintService.getActiveSprint().subscribe(sprint => {
            if (sprint === null) {
                this.displayStartButton = true;
            }
        });
    }

    drop(event: CdkDragDrop<Us[], any>): void {
        if (event.previousContainer === event.container) {
            moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
        } else {
            this.updateUsSprint(
                event.previousContainer.data[event.previousIndex],
                event.container.id
            );

            transferArrayItem(event.previousContainer.data,
                event.container.data,
                event.previousIndex,
                event.currentIndex);
        }
    }

    updateUsSprint(us: Us, sprintHtmlId: string): void {
        const sprintId = sprintHtmlId.substring(6);
        us.setSprint(Number(sprintId));
        this.usService.update(this.projectService.currentProject.getId(), us).subscribe();
    }

    onStart(): void {
        this.sprint.setState('active');
        this.sprintService.update(this.projectService.currentProject.getId(), this.sprint).subscribe(
            sprint => this.router.navigate(['/sprintActif'])
        );
    }
}
