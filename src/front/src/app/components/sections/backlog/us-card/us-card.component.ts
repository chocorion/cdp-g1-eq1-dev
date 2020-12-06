import {Component, Input, OnInit} from '@angular/core';
import {Us} from '../../../../models/us.model';

@Component({
    selector: 'app-us-card',
    templateUrl: './us-card.component.html',
    styleUrls: ['./us-card.component.css']
})
export class UsCardComponent implements OnInit {
    @Input() us: Us;
    @Input() disableEdit = false;
    constructor() {
    }

    ngOnInit(): void {
    }

}
