import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Task} from '../../../../models/task.model';
import {TaskService} from '../../../../services/task.service';

@Component({
  selector: 'app-expanded-task-card',
  templateUrl: './expanded-task-card.component.html',
  styleUrls: ['./expanded-task-card.component.css']
})
export class ExpandedTaskCardComponent implements OnInit {
  @Input() task: Task;
  @Output() stateChange = new EventEmitter<void>();

  constructor() { }

  ngOnInit(): void {
  }

}
