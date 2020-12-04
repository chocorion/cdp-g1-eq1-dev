import {Component, Input, OnInit} from '@angular/core';
import {Test} from '../../../../../models/test.model';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ProjectService} from '../../../../../services/project.service';
import {TestService} from '../../../../../services/test.service';

@Component({
    selector: 'app-edit-test-form',
    templateUrl: './edit-test-form.component.html',
    styleUrls: ['./edit-test-form.component.css']
})
export class EditTestFormComponent implements OnInit {
    @Input() test: Test;
    testName: string;
    testDescription: string;

    constructor(
        private projectService: ProjectService,
        private testService: TestService,
        private modalService: NgbModal
    ) {
    }

    ngOnInit(): void {
        this.testName = this.test.getName();
        this.testDescription = this.test.getDescription();
    }

    open(content): void {
        this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
            if (result === 'Save click') {
                const test = new Test(
                    this.testName,
                    this.testDescription,
                    this.test.getLastExecution(),
                    this.test.getState(),
                    this.projectService.currentProject.getId(),
                    this.test.getId()
                );

                this.testService.update(
                    this.projectService.currentProject.getId(),
                    test
                ).subscribe();
            }
        });
    }
}
