import {Component, Input, OnInit} from '@angular/core';
import {Us} from '../../../../../models/us.model';
import {UsService} from '../../../../../services/us.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'app-us-edit-form',
    templateUrl: './us-edit-form.component.html',
    styleUrls: ['./us-edit-form.component.css']
})
export class UsEditFormComponent implements OnInit {
    @Input() us: Us;

    usDescription: string;
    usPriority: string;
    usDifficulty: number;

    constructor(
        private usService: UsService,
        private modalService: NgbModal
    ) { }

    ngOnInit(): void {
        this.usDescription = this.us.getDescription();
        this.usPriority = this.us.getPriority();
        this.usDifficulty = this.us.getDifficulty();
    }

    open(content): void {
        this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
            if (result === 'Save click') {
                this.us.setDescription(this.usDescription);
                this.us.setPriority(this.usPriority);
                this.us.setDifficulty(this.usDifficulty);

                this.usService.update(
                    this.us.getProjectId(),
                    this.us
                ).subscribe();
            }
        });
    }
}
