import { Component, OnInit } from '@angular/core';
import {Project} from '../../../../models/project.model';
import {Subscription} from 'rxjs';
import { ProjectService } from 'src/app/services/project.service';
import { TaskService } from 'src/app/services/task.service';
import {Task} from '../../../../models/task.model';
import {Router} from '@angular/router';
@Component({
  selector: 'app-task-container',
  templateUrl: './task-container.component.html',
  styleUrls: ['./task-container.component.css']
})
export class TaskContainerComponent implements OnInit {
  currentProject: Project = null;
  currentProjectSubscription: Subscription;
  tasksTodo: Task[] = [];
  tasksDoing: Task[] = [];
  tasksDone: Task[] = [];

  constructor(
    private projectService: ProjectService,
    private taskService: TaskService,
    private router: Router)
    { }

  ngOnInit(): void {
    this.currentProjectSubscription = this.projectService.currentProjectSubject.subscribe(
      (project: Project) => {
          if (project === null) {
              this.router.navigate(['']);
              return;
          }
          this.currentProject = Project.fromJSON(project);
          this.updateTask();
      }
  );
    this.projectService.emitCurrentProject();
  }

  ngOnDestroy(): void {
    this.currentProjectSubscription.unsubscribe();
  }

  updateTask(): void {
    console.log('Updating task for project with id ' + this.currentProject.getId());
    this.taskService.getAllForProject(this.currentProject.getId()).subscribe(
        result => {
            this.tasksTodo = result.map(x => {
              if (x.status === 'TODO'){
                return Task.fromJSON(x);
              }
            });

            this.tasksDoing = result.map(x => {
              if (x.status === 'DOING'){
                return Task.fromJSON(x);
              }
            });

            this.tasksDone = result.map(x => {
              if (x.status === 'DONE'){
                return Task.fromJSON(x);
              }
            });
        }
    );
}

}
