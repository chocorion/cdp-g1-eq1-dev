import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Task } from '../../../../models/task.model';
import { TaskService } from '../../../../services/task.service';

@Component({
    selector: 'app-task-card',
    templateUrl: './task-card.component.html',
    styleUrls: ['./task-card.component.css']
})
export class TaskCardComponent implements OnInit {
    @Input() task: Task;
    @Input() tasks: Task[];
    expanded = false;
    dodOk: number;
    dodTotal: number;
    constructor(private taskService: TaskService) { }

    ngOnInit(): void {
        this.getCurrentDodStates();
    }

    expand(): void {
        this.expanded = !this.expanded;
    }

    getCurrentDodStates(): void {
        this.taskService.getDOD(this.task.getProjectId(), this.task.getId()).subscribe(
            result => {
                const t = result.filter(x => x.state === true);
                this.dodOk = t.length;
                this.dodTotal = result.length;
            }
        );
    }

}
