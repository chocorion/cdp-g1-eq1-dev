import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Member } from 'src/app/models/member.model';
import { Project } from 'src/app/models/project.model';
import { Task } from 'src/app/models/task.model';
import { MemberService } from 'src/app/services/member.service';
import { ProjectService } from 'src/app/services/project.service';
import { TaskService } from 'src/app/services/task.service';
import { UsService } from 'src/app/services/us.service';

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
    usIds = [];
    members = [];
    usSubscription: Subscription;
    private formBuilder: FormBuilder = new FormBuilder();
    checkParent: boolean[] = [];
    checkChild: boolean[] = [];

    constructor(
        private projectService: ProjectService,
        private taskService: TaskService,
        private usService: UsService,
        private memberService: MemberService,
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
                this.tasks.forEach(() => {
                    this.checkChild.push(false);
                    this.checkParent.push(false);
                });
                this.usIdList();
                this.memberList();
                if (this.form) {
                    this.form.patchValue({ taskId: this.tasks.length + 1 });
                }
            }
        );

        this.taskService.getAllForProject(this.projectService.currentProject.getId());

        this.form = this.formBuilder.group({
            taskId: this.tasks.length + 1,
            title: '',
            usId: '',
            duration: '',
            status: '',
            parents: '',
            children: '',
            member: 1,
        });
    }
    createTask(data: any): void {
        console.log(data);
        const task = new Task(data.taskId, this.currentProject.getId(), data.usId, data.member, data.title, data.duration, data.status);
        this.taskService.post(this.currentProject.getId(), task).subscribe(() => {
            for (let i = 0 ; i < this.checkParent.length ; i++){
                if (this.checkParent[i]){
                    this.taskService.addParentTask(this.currentProject.getId(), data.taskId, this.tasks[i]).subscribe(() => {});
                }
                if (this.checkChild[i]){
                    this.taskService.addChildrenTask(this.currentProject.getId(), data.taskId, this.tasks[i]).subscribe(() => {});
                }
        }
         });

    }


    usIdList(): void {
        this.usSubscription = this.usService.subject.subscribe(
            result => {
                this.usIds = result.map(x => x.getId());
            }
        );
        this.usService.getAllForProject(this.currentProject.getId());
    }

    memberList(): void {
        this.memberService.getMembers(this.currentProject.getId()).subscribe(
            result => {
                this.members = result.map(x => Member.fromJSON(x));
            }
        );
    }

}
