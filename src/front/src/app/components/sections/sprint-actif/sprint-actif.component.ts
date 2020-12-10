import { Component, OnInit } from '@angular/core';
import { Sprint } from 'src/app/models/sprint.model';
import { Us } from 'src/app/models/us.model';
import { ProjectService } from 'src/app/services/project.service';
import { SprintService } from 'src/app/services/sprint.service';
import {Router} from '@angular/router';

@Component({
    selector: 'app-sprint-actif',
    templateUrl: './sprint-actif.component.html',
    styleUrls: ['./sprint-actif.component.css']
})
export class SprintActifComponent implements OnInit {

    userStories: Us[] = [];
    sprint: Sprint;

    constructor(private sprintService: SprintService,
                private projectService: ProjectService,
                private router: Router) { }

    ngOnInit(): void {
        this.sprintService.getActiveSprint().subscribe(
            sprint => {
                this.sprint = sprint;
                this.sprintService.getUs(this.projectService.currentProject.getId(), this.sprint.getId()).subscribe(
                    usList => {
                        usList.forEach(us => this.userStories.push(Us.fromJSON(us)));
                    }
                );
            });
    }

    closeSprint(): void {
        this.sprint.setState('archived');
        this.sprintService.update(this.projectService.currentProject.getId(), this.sprint).subscribe(() => { });
        this.router.navigate(['project', this.projectService.currentProject.getId(), 'backlog']);
    }

}
