import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
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

    UserStories = [];
    members = [];
    usSubscription: Subscription;
    private formBuilder: FormBuilder = new FormBuilder();
    estimateValue: number;

    constructor(
        private projectService: ProjectService,
        private taskService: TaskService,
        private usService: UsService,
        private memberService: MemberService,
        private router: Router,
        private route: ActivatedRoute) {
    }

    ngOnInit(): void {

        this.form = this.formBuilder.group({
            title: '',
            usId: null,
            duration: '',
            status: 'TODO',
            member: 1,
        });

        this.route.paramMap.subscribe(params => {
            this.projectService.getProject(parseInt(params.get('projectId'), 10)).subscribe(
                project => {
                    this.projectService.setCurrentProject(Project.fromJSON(project));
                    this.currentProject = this.projectService.currentProject;

                    this.tasksSubscription = this.taskService.getSubject(this.currentProject.getId()).subscribe(
                        result => {
                            this.tasks = result;
                            this.usIdList();
                            this.memberList();
                            this.getEstimateValue();
                        }
                    );

                    this.projectService.emitCurrentProject();
                }
            );
        });
    }


    createTask(data: any): void {
        const todo = 'TODO';
        if (!Number.isNaN(parseInt(data.duration))) {
            const task = new Task(-1, this.currentProject.getId(), data.usId, data.member, data.title, data.duration, todo);
            this.taskService.post(this.currentProject.getId(), task).subscribe(() => { });
        }

    }


    usIdList(): void {
        this.usSubscription = this.usService.getSubject(this.currentProject.getId()).subscribe(
            result => {
                this.UserStories = result;
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

    getEstimateValue(): void {
        this.estimateValue = 0;
        this.tasks.forEach(tasks => this.estimateValue = this.estimateValue + parseInt(tasks.getDuration()));
    }

}
