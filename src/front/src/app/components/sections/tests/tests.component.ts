import {Component, OnDestroy, OnInit} from '@angular/core';
import {ProjectService} from '../../../services/project.service';
import {Project} from '../../../models/project.model';
import {Subscription} from 'rxjs';
import {Test} from '../../../models/test.model';
import {TestService} from '../../../services/test.service';
import {Router} from '@angular/router';

@Component({
    selector: 'app-tests',
    templateUrl: './tests.component.html',
    styleUrls: ['./tests.component.css']
})
export class TestsComponent implements OnInit, OnDestroy {
    currentProject: Project = null;

    currentProjectSubscription: Subscription;
    testsSubscription: Subscription;

    successRate = 0;
    failureRate = 0;
    tests: Test[] = [];

    constructor(
        private projectService: ProjectService,
        private testService: TestService,
        private router: Router
    ) {
    }

    ngOnInit(): void {
        this.currentProjectSubscription = this.projectService.currentProjectSubject.subscribe(
            (project: Project) => {
                if (project === null) {
                    this.router.navigate(['']);
                    return;
                }
                this.currentProject = Project.fromJSON(project);
                this.updateTests();
            }
        );

        this.projectService.emitCurrentProject();

        this.testsSubscription = this.testService.subject.subscribe(
            tests => {
                this.tests = tests;
                this.updateTests();
            }
        );
        this.testService.getAllForProject(this.projectService.currentProject.getId());
    }

    ngOnDestroy(): void {
        this.currentProjectSubscription.unsubscribe();
        this.testsSubscription.unsubscribe();
    }

    updateTests(): void {
        this.updatePercentFailure();
        this.updatePercentSuccess();
    }

    updatePercentSuccess(): void {
        this.successRate = this.tests.filter(x => x.getState() === 'validate').length / this.tests.length * 100;
    }

    updatePercentFailure(): void {
        this.failureRate = this.tests.filter(x => x.getState() === 'refused').length / this.tests.length * 100;
    }
}
