import {Component, Input, OnInit} from '@angular/core';
import {Task} from '../../../../../models/task.model';

@Component({
    selector: 'app-simple-task',
    templateUrl: './simple-task.component.html',
    styleUrls: ['./simple-task.component.css']
})
export class SimpleTaskComponent implements OnInit {
    @Input() task: Task;

    constructor() { }

    ngOnInit(): void {
    }

}
