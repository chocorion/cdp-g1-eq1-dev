import { Component, OnDestroy, OnInit } from '@angular/core';
import { Project } from '../../../../models/project.model';
import { Subscription } from 'rxjs';
import { ProjectService } from 'src/app/services/project.service';
import { TaskService } from 'src/app/services/task.service';
import { Task } from '../../../../models/task.model';
import { Router } from '@angular/router';
import { CdkDragDrop, CdkDragEnd, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';

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
        }
        else if (event.container.id === 'DOING' && event.previousContainer.id === 'TODO'){
            let task: Task;
            task = this.tasksTodo[event.previousIndex];
            if (task){
                this.taskService.getDOD(task.getProjectId(), task.getId()).subscribe(
                    result => {
                        const t = result.filter(x => x.state === true);
                        const dodTotal = result.length;
                        if (dodTotal > 0){
                            transferArrayItem(event.previousContainer.data,
                                event.container.data,
                                event.previousIndex, event.currentIndex);
                            event.container.data[event.currentIndex].status = event.container.id;
                            this.updateTaskState(event);
                        }
                    }
                );
            }
        }
        else if (event.container.id !== 'DONE') {
            transferArrayItem(event.previousContainer.data,
                event.container.data,
                event.previousIndex, event.currentIndex);
            event.container.data[event.currentIndex].status = event.container.id;
            this.updateTaskState(event);
        }
        else {
            let task: Task;
            if (event.previousContainer.id === 'TODO'){
                task = this.tasksTodo[event.previousIndex];
            }
            else if (event.previousContainer.id === 'DOING'){
                task = this.tasksDoing[event.previousIndex];
            }
            if (task){
                this.taskService.getDOD(task.getProjectId(), task.getId()).subscribe(
                    result => {
                        const t = result.filter(x => x.state === true);
                        const dodOk = t.length;
                        const dodTotal = result.length;
                        if (dodOk === dodTotal && dodOk > 0 && dodTotal > 0){
                            transferArrayItem(event.previousContainer.data,
                                event.container.data,
                                event.previousIndex, event.currentIndex);
                            event.container.data[event.currentIndex].status = event.container.id;
                            this.updateTaskState(event);
                        }
                    }
                );
            }
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
                this.currentProject = Project.fromJSON(project);
                this.updateTask();

                this.taskService.getAllForProject(this.projectService.currentProject.getId());
            }
        );

        this.tasksSubscription = this.taskService.subject.subscribe(
            result => {
                this.tasks = result;
                this.updateTask();
            }
        );
    }

    ngOnDestroy(): void {
        this.currentProjectSubscription.unsubscribe();
        this.tasksSubscription.unsubscribe();
    }
}
