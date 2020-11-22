import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Task} from '../../../../models/task.model';
import {TaskService} from '../../../../services/task.service';

@Component({
  selector: 'app-task-card',
  templateUrl: './task-card.component.html',
  styleUrls: ['./task-card.component.css']
})
export class TaskCardComponent implements OnInit {
  @Input() task: Task;
  @Output() stateChange = new EventEmitter<void>();
  constructor() { }

  ngOnInit(): void {
  }

}
