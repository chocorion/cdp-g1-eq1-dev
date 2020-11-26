import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { DOD } from 'src/app/models/dod.model';
import { Member } from 'src/app/models/member.model';
import { MemberService } from 'src/app/services/member.service';
import { UsService } from 'src/app/services/us.service';
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
    @Output() stateChange = new EventEmitter<any>();
    @Input() tasks: Task[];
    checkParent: boolean[] = [];
    checkChild: boolean[] = [];
    parentDependency: Task[] = [];
    childrenDependency: Task[] = [];
    dods: DOD[] = [];

    combinaisons = [];
    usIds = [];
    projectMembers = [];
    currentMember: Member = new Member(null, null, null, null);

    usSubscription: Subscription;
    private formBuilder: FormBuilder = new FormBuilder();
    form: any;
    modify = false;

    constructor(
        private taskService: TaskService,
        private usService: UsService,
        private memberService: MemberService,
        private router: Router) {
    }

    ngOnInit(): void {
        this.tasks.forEach(() => {
            this.checkChild.push(false);
            this.checkParent.push(false);
        });
        this.getDependencies();
        this.getDOD();
        this.combinaison();
        this.usIdList();
        this.memberList();
        this.getMember();

        this.form = this.formBuilder.group({
            title: this.task.getTitle(),
            usId: this.task.getUsId(),
            duration: this.task.getDuration(),
            status: this.task.getStatus(),
            parents: this.getParents(),
            children: this.getChildren(),
            member: this.getMember(),
        });
    }

    usIdList(): void {
        this.usSubscription = this.usService.subject.subscribe(
            result => {
                this.usIds = result.map(x => x.getId());
            }
        );
        this.usService.getAllForProject(this.task.getProjectId());
    }

    memberList(): void {
        this.memberService.getMembers(this.task.getProjectId()).subscribe(
            result => {
                this.projectMembers = result.map(x => Member.fromJSON(x));
                this.form.patchValue({ member: this.getMember() });
            }
        );
    }

    getMember(): void {
        if (this.task.getMemberId() == null) {
            this.currentMember = null;
        } else {
            this.memberService.getMember(this.task.getMemberId(), this.task.getProjectId()).subscribe(
                result => {
                    this.currentMember = Member.fromJSON(result);
                }
            );
        }
    }

    getMemberName(): string {
        if (this.currentMember) { return this.currentMember.getName(); }
        return '';
    }

    combinaison(): void {
        let array = this.tasks.map(v => v.getId());
        array = array.filter(x => x !== this.task.getId());
        const results = [];

        array.forEach(item => {
            const t = results.map(row => [...row, item]);
            results.push(...t);
            results.push([item]);
        });
        this.combinaisons = results.map(x => x.join(', '));
    }

    emitExpand(): void {
        this.expand.emit();
    }

    emitStateChange(): void {
        this.stateChange.emit();
    }

    updateModify(): void {
        this.modify = !this.modify;
    }

    delete(): void {
        this.taskService.delete(this.task.getProjectId(), this.task);
        this.modify = !this.modify;
        this.router.routeReuseStrategy.shouldReuseRoute = () => false;
        this.router.onSameUrlNavigation = 'reload';
        this.router.navigate(['tasks']);
    }

    getDependencies(): void {
        this.taskService.getChildrenTasks(this.task.getProjectId(), this.task.getId()).subscribe(
            result => {
                this.childrenDependency = result.map(t => Task.fromJSON(t));
                this.form.patchValue({ children: this.getChildren() });
                this.childrenDependency.forEach( t => this.checkChild[t.getId() - 1] = true);
            }
        );
        this.taskService.getParentTasks(this.task.getProjectId(), this.task.getId()).subscribe(
            result => {
                this.parentDependency = result.map(t => Task.fromJSON(t));
                this.form.patchValue({ parents: this.getParents()});
                this.parentDependency.forEach( t => this.checkParent[t.getId() - 1] = true);
            }
        );

    }

    updateParent(task: Task): void{
        if (!this.checkParent[task.getId() - 1]){
            this.taskService.deleteParentTask(this.task.getProjectId(), this.task.getId(), task.getId()).subscribe(() => {});
        }
        else if (this.checkParent[task.getId() - 1]){
            this.taskService.addParentTask(this.task.getProjectId(), this.task.getId(),
                    this.tasks.find(e => e.getId() === task.getId())).subscribe
                    ((() => this.checkParent[task.getId() - 1] = true));
        }
    }

    updateChild(task: Task): void{
        if (!this.checkChild[task.getId() - 1]) {
            this.taskService.deleteChildrenTask(this.task.getProjectId(), this.task.getId(), task.getId()).subscribe(() => {});
        }
        else if (this.checkChild[task.getId() - 1]){
            this.taskService.addChildrenTask(this.task.getProjectId(), this.task.getId(),
                    this.tasks.find(e => e.getId() === task.getId())).subscribe
                    ((() => this.checkChild[task.getId() - 1] = true));
        }

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
            () => { this.emitStateChange(); }
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
        this.task = new Task(this.task.getId(), this.task.getProjectId(), data.usId, data.member, data.title, data.duration, data.status);
        this.taskService.update(this.task.getProjectId(), this.task).subscribe(
            () => { }
        );
    }

}
