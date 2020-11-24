import {Component, OnDestroy, OnInit} from '@angular/core';
import {Project} from '../../../../models/project.model';
import {Subscription} from 'rxjs';
import {ProjectService} from 'src/app/services/project.service';
import {TaskService} from 'src/app/services/task.service';
import {Task} from '../../../../models/task.model';
import {Router} from '@angular/router';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';

@Component({
    selector: 'app-task-container',
    templateUrl: './task-container.component.html',
    styleUrls: ['./task-container.component.css']
})
export class TaskContainerComponent implements OnInit, OnDestroy {
    currentProject: Project = null;
    currentProjectSubscription: Subscription;
    tasksSubscription: Subscription;

    tasks: Task[] = [];

    tasksTodo: Task[] = [];
    tasksDoing: Task[] = [];
    tasksDone: Task[] = [];
    
    constructor(
        private projectService: ProjectService,
        private taskService: TaskService,
        private router: Router) {
    }



    

    updateTask(): void {
        this.tasksTodo = this.tasks.filter(x => x.status === 'TODO');
        this.tasksDoing = this.tasks.filter(x => x.status === 'DOING');
        this.tasksDone = this.tasks.filter(x => x.status === 'DONE');
    }
    
  dropped(event: CdkDragDrop<any>): void {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data,
        event.previousIndex,
        event.currentIndex);
    } else {
      transferArrayItem(event.previousContainer.data,
        event.container.data,
        event.previousIndex, event.currentIndex);
      event.container.data[event.currentIndex].status = event.container.id;
      this.updateTaskState(event);
    }
  }

  updateTaskState(event: CdkDragDrop<any>): void {
    event.container.data[event.currentIndex].status = event.container.id;
    let task: Task;
    switch (event.container.id) {
      case 'TODO':
        task = this.tasksTodo[event.currentIndex];
        break;
      case 'DOING':
        task = this.tasksDoing[event.currentIndex];
        break;
      default:
        task = this.tasksDone[event.currentIndex];
        break;
    }

    this.update(task);
  }

  update(task: Task): void {
    this.taskService.update(task.getProjectId(), task).subscribe(
      () => { }
    );
  }
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

        this.tasksSubscription = this.taskService.subject.subscribe(
            result => {
                this.tasks = result;
                this.updateTask();
            }
        );

        this.taskService.getAllForProject(this.projectService.currentProject.getId());
    }

    ngOnDestroy(): void {
        this.currentProjectSubscription.unsubscribe();
        this.tasksSubscription.unsubscribe();
    }
}
