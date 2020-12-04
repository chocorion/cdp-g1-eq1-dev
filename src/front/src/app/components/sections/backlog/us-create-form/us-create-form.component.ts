import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Us} from '../../../../models/us.model';
import {ProjectService} from '../../../../services/project.service';
import {UsService} from '../../../../services/us.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'app-us-create-form',
    templateUrl: './us-create-form.component.html',
    styleUrls: ['./us-create-form.component.css']
})
export class UsCreateFormComponent implements OnInit {
    usForm: FormGroup;

    constructor(
        private formBuilder: FormBuilder,
        private projectService: ProjectService,
        private usService: UsService,
        private modalService: NgbModal
    ) {
        this.usForm = formBuilder.group({
            description: '',
            priority: '',
            difficulty: ''
        });
    }

    ngOnInit(): void {
    }

    open(content): void {
        this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
            if (result === 'Save click') {
                const us = new Us(
                    null,
                    this.projectService.currentProject.getId(),
                    this.usForm.value.priority,
                    this.usForm.value.description,
                    Number(this.usForm.value.difficulty),
                    null
                );

                this.usService.post(
                    this.projectService.currentProject.getId(),
                    us
                ).subscribe(next => console.log('Us added !'));
            }
        });
    }
}
