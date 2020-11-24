import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder } from '@angular/forms';
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

  private formBuilder: FormBuilder = new FormBuilder();
  form: any;
  modify = false;

  constructor(
    private taskService: TaskService) {
     }

  ngOnInit(): void {
    this.getDependencies();
    this.getDOD();
    this.form = this.formBuilder.group({
      title: this.task.getTitle(),
      usId: this.task.getUsId(),
      duration: this.task.getDuration(),
      status: this.task.getStatus(),
      parents: this.getParents(),
      children: this.getChildren(),
      member: '',
    });
  }

  emitExpand(): void {
    this.expand.emit();
  }

  updateModify(): void{
    this.modify = !this.modify;
  }

  getDependencies(): void {
    this.taskService.getChildrenTasks(this.task.getProjectId(), this.task.getId()).subscribe(
      result => {
        this.childrenDependency = result.map(t => Task.fromJSON(t));
        this.form.patchValue({children : this.getChildren()});
      }
    );
    this.taskService.getParentTasks(this.task.getId(), this.task.getId()).subscribe(
      result => {
        this.parentDependency = result.map(t => Task.fromJSON(t));
        this.form.patchValue({parents : this.getParents()});
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

  onSubmit(data: any): void {
    this.task = new Task(this.task.getId(), this.task.getProjectId(), data.usId, data.title, data.duration, data.status);
    this.taskService.update(this.task.getProjectId(), this.task).subscribe(
      () => {}
    );
    // Then update parents etc...

  }

}
