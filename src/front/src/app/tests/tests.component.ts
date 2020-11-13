import {Component, OnDestroy, OnInit} from '@angular/core';
import {ProjectService} from '../services/project.service';
import {Project} from '../model/project.model';
import {Subscription} from 'rxjs';
import {Test} from '../model/test.model';
import {TestService} from '../services/test.service';
import {Router} from '@angular/router';

@Component({
    selector: 'app-tests',
    templateUrl: './tests.component.html',
    styleUrls: ['./tests.component.css']
})
export class TestsComponent implements OnInit, OnDestroy {
    currentProject: Project = null;
    currentProjectSubscription: Subscription;
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
                // @ts-ignore
                this.currentProject = new Project(project.id, project.name, project.description);
                this.updateTests();
            }
        );

        this.projectService.emitCurrentProject();
    }

    ngOnDestroy(): void {
        this.currentProjectSubscription.unsubscribe();
    }

    updateTests(): void {
        this.testService.getAllForProject(this.currentProject.getId()).subscribe(
            result => {
                this.tests = result.map(x => Test.fromJSON(x));
                this.tests.forEach(t => console.log('Display test with date ' + t.getLastExecution()));
            }
        );
    }
}
