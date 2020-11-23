import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { DOD } from 'src/app/models/dod.model';
import { Task } from '../../../../models/task.model';
import { TaskService } from '../../../../services/task.service';



@Component({
  selector: 'app-expanded-task-card',
  templateUrl: './expanded-task-card.component.html',
  styleUrls: ['./expanded-task-card.component.css']
})
export class ExpandedTaskCardComponent implements OnInit {
  @Input() task: Task;
  @Output() expand = new EventEmitter<any>();
  parentDependency: Task[] = [];
  childrenDependency: Task[] = [];
  dods: DOD[] = [];

  constructor(
    private taskService: TaskService) { }

  ngOnInit(): void {
    this.getDependencies();
    this.getDOD();
  }

  emitExpand(): void {
    this.expand.emit();
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

  getDOD(): void {
    this.taskService.getDOD(this.task.getProjectId(), this.task.getId()).subscribe(
      result => {
        this.dods = result.map(t => DOD.fromJSON(t));
      }
    );
  }

  updateDOD(dod: DOD): void {
    this.taskService.updateDOD(this.task.getProjectId(), this.task.getId(), dod).subscribe(
      () => { }
    );
  }

  getChildren(): string {
    let s = '';
    this.childrenDependency.forEach(x => s += x.getId() + ', ');
    return s.slice(0, -2);
  }

  getParents(): string {
    let s = '';
    this.parentDependency.forEach(x => s += x.getId() + ', ');
    return s.slice(0, -2);
  }

}
