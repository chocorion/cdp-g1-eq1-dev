import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {TestService} from '../../../../services/test.service';
import {Test} from '../../../../models/test.model';
import {ProjectService} from '../../../../services/project.service';

@Component({
    selector: 'app-test-form',
    templateUrl: './test-form.component.html',
    styleUrls: ['./test-form.component.css']
})
export class TestFormComponent implements OnInit {
    testForm: FormGroup;

    constructor(
        private formBuilder: FormBuilder,
        private modalService: NgbModal,
        private testService: TestService,
        private projectService: ProjectService
    ) {
        this.testForm = this.formBuilder.group({
            name: '',
            description: ''
        });
    }

    ngOnInit(): void {
    }

    open(content): void {
        this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
            if (result === 'Save click') {
                const test = new Test(
                    this.testForm.value.name,
                    this.testForm.value.description,
                    null,
                    'not executed',
                    this.projectService.currentProject.getId()
                );

                this.testService.post(
                    this.projectService.currentProject.getId(),
                    test
                ).subscribe(next => console.log('Test added !'));
            }
        });
    }
}
