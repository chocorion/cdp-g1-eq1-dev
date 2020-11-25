import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Project } from 'src/app/models/project.model';
import { Task } from 'src/app/models/task.model';
import { ProjectService } from 'src/app/services/project.service';
import { TaskService } from 'src/app/services/task.service';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css']
})
export class TasksComponent implements OnInit {
  form: any;
  tasks: Task[] = [];
  currentProject: Project = null;
  currentProjectSubscription: Subscription;
  tasksSubscription: Subscription;
  combinaisons = [];
  private formBuilder: FormBuilder = new FormBuilder();

  constructor(
    private projectService: ProjectService,
    private taskService: TaskService,
    private router: Router) {
  }

  ngOnInit(): void {

    this.currentProjectSubscription = this.projectService.currentProjectSubject.subscribe(
      (project: Project) => {
        if (project === null) {
          this.router.navigate(['']);
          return;
        }
        this.currentProject = Project.fromJSON(project);
      }
    );
    this.projectService.emitCurrentProject();

    this.tasksSubscription = this.taskService.subject.subscribe(
      result => {
        this.tasks = result;
        this.combinaison();
      }
    );

    this.taskService.getAllForProject(this.projectService.currentProject.getId());

    this.form = this.formBuilder.group({
      taskId: 0,
      title: '',
      usId: '',
      duration: '',
      status: '',
      parents: '',
      children: '',
      member: '',
    });
  }

  combinaison(): void {
    let array = this.tasks.map(v => v.getId());
    const results = [];

    array.forEach(item => {
      const t = results.map(row => [...row, item]);
      results.push(...t);
      results.push([item]);
    });
    this.combinaisons = results.map(x => x.join(', '));
  }

  createTask(data: any): void {
    const task = new Task(data.taskId, this.currentProject.getId(), data.usId, data.title, data.duration, data.status);
    this.taskService.post(this.currentProject.getId(), task).subscribe( () => {});
  }

}
