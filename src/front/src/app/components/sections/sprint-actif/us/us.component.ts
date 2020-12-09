import {Component, Input, OnInit} from '@angular/core';
import {Us} from '../../../../models/us.model';

@Component({
    selector: 'app-us',
    templateUrl: './us.component.html',
    styleUrls: ['./us.component.css']
})
export class UsComponent implements OnInit {
    @Input() us: Us;
    displayTask: boolean;

    constructor() {
    }

    ngOnInit(): void {
        this.displayTask = false;
    }
}
