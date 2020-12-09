import { Component, OnDestroy, OnInit } from '@angular/core';
import { Project } from '../../../../models/project.model';
import { Subscription } from 'rxjs';
import { ProjectService } from 'src/app/services/project.service';
import { TaskService } from 'src/app/services/task.service';
import { Task } from '../../../../models/task.model';
import { Router } from '@angular/router';
import { CdkDragDrop, CdkDragEnd, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { FormBuilder } from '@angular/forms';
import { MemberService } from 'src/app/services/member.service';
import { Member } from 'src/app/models/member.model';
import { SprintService } from 'src/app/services/sprint.service';
import { UsService } from 'src/app/services/us.service';
import { Us } from 'src/app/models/us.model';

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

    private formBuilder: FormBuilder = new FormBuilder();
    taskFilterForm: any;
    values: string[] = [];
    members = [];
    sprints = [];
    ussprint: Us = new Us(null, null, null, null, null, null);
    tasksprint = [];

    constructor(
        private projectService: ProjectService,
        private taskService: TaskService,
        private memberService: MemberService,
        private sprintService: SprintService,
        private usService: UsService,
        private router: Router) {
        this.taskFilterForm = this.formBuilder.group({
            type: '',
            value: ''
        });
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
            if (event.previousContainer.id === 'TODO') {
                task = this.tasksTodo[event.previousIndex];
            }
            else if (event.previousContainer.id === 'DOING') {
                task = this.tasksDoing[event.previousIndex];
            }
            if (task) {
                this.taskService.getDOD(task.getProjectId(), task.getId()).subscribe(
                    result => {
                        const t = result.filter(x => x.state === true);
                        const dodOk = t.length;
                        const dodTotal = result.length;
                        if (dodOk === dodTotal && dodOk > 0 && dodTotal > 0) {
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

                this.taskService.getAllForProject(this.projectService.currentProject.getId());

                this.sprintService.getAllForProject(this.projectService.currentProject.getId());

                this.getProjectMembers();

                this.getProjectSprints();

            }
        );

        this.tasksSubscription = this.taskService.subject.subscribe(
            result => {
                this.tasks = result;
                this.updateTask();
                this.getTasksSprints();
            }
        );

    }

    ngOnDestroy(): void {
        this.currentProjectSubscription.unsubscribe();
        this.tasksSubscription.unsubscribe();
    }


    getSprint(task: Task): void {

        this.usService.getById(this.currentProject.getId(), task.getUsId()).subscribe(
            result => {
                this.ussprint = Us.fromJSON(result);
                this.tasksprint.splice(task.getId() - 1, 0, this.ussprint.getSprint());
            }
        );
    }

    getTasksSprints(): void {
        this.tasksprint = [];
        for (const task of this.tasks) {
            this.getSprint(task);
        }

    }

    refreshTasks(): void {
        this.taskService.getAllForProject(this.projectService.currentProject.getId());
    }

    onSubmitFilter(data): void {
        if (data.type === 'member') {
            this.tasks = this.tasks.filter(x => x.getMemberId() === parseInt(data.value.split(',', 1), 10));
        } else if (data.type === 'sprint') {
            this.tasks = this.tasks.filter(x => this.tasksprint[x.getId() - 1] === parseInt(data.value, 10));
        }
        this.updateTask();
    }

    getProjectMembers(): void {
        this.memberService.getMembers(this.currentProject.getId()).subscribe(
            result => {
                this.members = result.map(x => Member.fromJSON(x));
            }
        );
    }

    getProjectSprints(): void {
        this.sprintService.subject.subscribe(
            sprintList => {
                this.sprints = sprintList;
            }
        );
    }

    onChange(data): void {
        this.values = [];
        switch (data) {
            case 'member':
                this.members.forEach(x => this.values.push(x.getUser() + ',' + x.getName()));
                break;
            case 'sprint':
                this.sprints.forEach(x => this.values.push(x.getId().toString()));
                break;
            default:
                console.log('wrong filter type');
        }
    }
}
