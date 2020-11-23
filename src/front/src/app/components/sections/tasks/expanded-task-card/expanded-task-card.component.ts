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

  parentDependency: Task[] = [];
  childrenDependency: Task[] = [];

  constructor(
    private taskService: TaskService)
    { }

    ngOnInit(): void {
      this.getDependencies();
    }

  getDependencies(): void {
    this.taskService.getChildrenTasks(this.task.getProjectId(), this.task.getId()).subscribe(
      result => {
        this.childrenDependency = result.map(t => Task.fromJSON(t));
      }
    );
    this.taskService.getParentTasks(this.task.getId(), this.task.getId()).subscribe(
      result => {
        this.parentDependency = result.map(t => Task.fromJSON(t));
      }
    );
  }

  getChildren(): string{
    let s = '';
    this.childrenDependency.forEach(x => s += x.getId() + ', ');
    return s.slice(0, -2);
  }

  getParents(): string{
    let s = '';
    this.parentDependency.forEach(x => s += x.getId() + ', ');
    return s.slice(0, -2);
  }

}
