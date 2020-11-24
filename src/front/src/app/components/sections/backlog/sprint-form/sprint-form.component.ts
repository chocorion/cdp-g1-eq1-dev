import {Component, OnInit} from '@angular/core';
import {Sprint} from '../../../../models/sprint.model';
import {FormBuilder, FormGroup} from '@angular/forms';
import {ProjectService} from '../../../../services/project.service';
import {SprintService} from '../../../../services/sprint.service';

import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'app-sprint-form',
    templateUrl: './sprint-form.component.html',
    styleUrls: ['./sprint-form.component.css']
})
export class SprintFormComponent implements OnInit {
    sprintForm: FormGroup;
    constructor(
        private projectService: ProjectService,
        private sprintService: SprintService,
        private formBuilder: FormBuilder,
        private modalService: NgbModal
    ) {
        this.sprintForm = this.formBuilder.group({
            name: ''
        });
    }

    ngOnInit(): void {
    }


    onSprintSubmit(value: any): void {
        const newSprint = new Sprint(
            value.name,
            this.projectService.currentProject.getId()
        );

        this.sprintService.post(newSprint.getProjectId(), newSprint).subscribe(
            sprint => {
                console.log('sprint was added !');
            }
        );
        console.log('Adding sprint ' + value.name);
    }

    open(content): void {
        this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
            console.log(`Closed with: ${result}`);
        }, (reason) => {
            console.log(`Dismissed ${this.getDismissReason(reason)}`);
        });
    }

    private getDismissReason(reason: any): string {
        if (reason === ModalDismissReasons.ESC) {
            return 'by pressing ESC';
        } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
            return 'by clicking on a backdrop';
        } else {
            return `with: ${reason}`;
        }
    }
}
