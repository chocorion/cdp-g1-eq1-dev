import { Component, OnInit, ViewChild } from '@angular/core';
import { Sprint } from 'src/app/models/sprint.model';
import { Us } from 'src/app/models/us.model';
import { ProjectService } from 'src/app/services/project.service';
import { SprintService } from 'src/app/services/sprint.service';
import { Router } from '@angular/router';
import { Task } from 'src/app/models/task.model';
import { TaskService } from 'src/app/services/task.service';
import { takeLast } from 'rxjs/operators';
import { CloseSprintErrorComponent } from './close-sprint-error/close-sprint-error.component';

@Component({
    selector: 'app-sprint-actif',
    templateUrl: './sprint-actif.component.html',
    styleUrls: ['./sprint-actif.component.css']
})
export class SprintActifComponent implements OnInit {

    userStories: Us[] = [];
    sprint: Sprint;
    tasks: Task[] = [];

    @ViewChild('child')
    private child: CloseSprintErrorComponent;

    constructor(private sprintService: SprintService,
                private projectService: ProjectService,
                private taskService: TaskService,
                private router: Router) { }

    ngOnInit(): void {
        this.sprintService.getActiveSprint().subscribe(
            sprint => {
                this.sprint = sprint;
                this.sprintService.getUs(this.projectService.currentProject.getId(), this.sprint.getId()).subscribe(
                    usList => {
                        usList.forEach(us => this.userStories.push(Us.fromJSON(us)));
                        this.userStories.forEach(
                            us => this.taskService.getAllForUs(this.projectService.currentProject.getId(), us.getId()).subscribe(
                                tasks => tasks.forEach(task => this.tasks.push(Task.fromJSON(task)))));
                    }
                );
            }
        );
    }


    closeSprintTest(): void {
        let count = 0;
        this.tasks.forEach(task => {
            if (task.getStatus() === 'DONE') { count += 1; }
        });
        if (count === this.tasks.length) {
            this.closeSprint();
        } else {
            this.child.open();
        }
    }
    closeSprint(): void {
        this.sprint.setState('archived');
        this.sprintService.update(this.projectService.currentProject.getId(), this.sprint).subscribe(() => { });
        this.router.navigate(['project', this.projectService.currentProject.getId(), 'backlog']);
    }
}
