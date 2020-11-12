import {Component, Input, OnInit} from '@angular/core';
import {Test} from '../../model/test.model';

@Component({
    selector: 'app-test-item',
    templateUrl: './test-item.component.html',
    styleUrls: ['./test-item.component.css']
})
export class TestItemComponent implements OnInit {
    @Input() test: Test;
    constructor() {
    }

    ngOnInit(): void {
    }

    getColor(): string {
        switch (this.test.getState()) {
            case 'validate': {
                return 'bg-success';
            }

            case 'refused': {
                return 'bg-danger';
            }

            default:
                return 'bg-light';
        }
    }
}
