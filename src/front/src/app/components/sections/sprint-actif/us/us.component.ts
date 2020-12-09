import {Component, Input, OnInit} from '@angular/core';
import { Task } from 'src/app/models/task.model';
import {Us} from '../../../../models/us.model';
import {TaskService} from '../../../../services/task.service';
import {ProjectService} from '../../../../services/project.service';

@Component({
    selector: 'app-us',
    templateUrl: './us.component.html',
    styleUrls: ['./us.component.css']
})
export class UsComponent implements OnInit {
    @Input() us: Us;

    displayTask: boolean;
    tasks: Task[];

    constructor(
        private taskService: TaskService,
        private projectService: ProjectService
    ) {
    }

    ngOnInit(): void {
        this.displayTask = false;
        this.tasks = [];

        this.taskService.getAllForUs(this.projectService.currentProject.getId(), this.us.getId()).subscribe(
            tasks => {
                tasks.forEach(task => {
                    const currentTask = Task.fromJSON(task);

                    if (currentTask.getStatus().toLowerCase() === 'todo') {
                        this.tasks.push(currentTask);
                    }
                });
            }
        );
    }

    onClick(): void {
        this.displayTask = !this.displayTask;
    }
}
