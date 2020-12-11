import { Component, OnInit } from '@angular/core';
import {TaskService} from '../../../../services/task.service';
import {SprintService} from '../../../../services/sprint.service';
import {Us} from '../../../../models/us.model';
import { Task } from 'src/app/models/task.model';
import {Subject} from 'rxjs';

@Component({
    selector: 'app-dependency-graph',
    templateUrl: './dependency-graph.component.html',
    styleUrls: ['./dependency-graph.component.css']
})
export class DependencyGraphComponent implements OnInit {
    public links: any[] = [];
    public nodes: any[] = [];

    update$: Subject<any> = new Subject();

    constructor(
        private taskService: TaskService,
        private sprintService: SprintService
    ) {}

    ngOnInit(): void {
        this.sprintService.getActiveSprint().subscribe(
            sprint => {
                if (sprint !== null) {
                    this.sprintService.getUs(sprint.getProjectId(), sprint.getId()).subscribe(
                        us => {
                            this.extractTaskFromUs(us);
                            console.log(this.links);
                        }
                    );
                }
            }
        );
    }

    extractTaskFromUs(userStories: Us[]): void {
        this.links = [];
        this.nodes = [{
            id: 'end',
            label: 'end'
        }];

        userStories.forEach(us => {
            us = Us.fromJSON(us);

            this.taskService.getAllForUs(us.getProjectId(), us.getId()).subscribe(
                tasks => {
                    tasks.forEach(task => this.addTaskToGraph(Task.fromJSON(task)));
                }
            );
        });
    }

    private addInNodeIfAbsent(task: Task): void {
        const index = this.nodes.findIndex(e => e.id === task.getId());

        if (index === -1) {
            this.nodes.push({
                id: task.getId(),
                label: task.getId()
            });

            this.addRelationToEnd(task);
        }
    }

    private addRelationToEnd(task: Task): void {
        this.links.push({
            id: `link${task.getId()}_end`,
            source: task.getId(),
            target: 'end',
            label: task.getDuration() + ' h.h'
        });
    }

    private removeRelationToEnd(task: Task): void {
        const index = this.links.findIndex(e => e.id === `link${task.getId()}_end`);
        if (index !== -1) {
            this.links.splice(index, 1);
        }
    }

    private addRelation(parent: Task, child: Task): void {

        this.links.push({
            id: `link${parent.getId()}_${child.getId()}`,
            source: parent.getId(),
            target: child.getId(),
            label: parent.getDuration() + ' h.h'
        });

        this.removeRelationToEnd(parent);
    }

    public addTaskToGraph(task: Task): void {
        this.addInNodeIfAbsent(task);

        this.taskService.getChildrenTasks(task.getProjectId(), task.getId()).subscribe(
            children => {
                children.forEach(child => {
                    child = Task.fromJSON(child);

                    this.addInNodeIfAbsent(child);
                    this.addRelation(task, child);
                });

                this.update$.next(true);
            }
        );
    }

    onNodeSelect($event: any): void {
        console.log('click on ' + $event);
    }
}
