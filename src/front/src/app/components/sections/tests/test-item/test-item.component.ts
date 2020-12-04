import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Test} from '../../../../models/test.model';
import {TestService} from '../../../../services/test.service';

@Component({
    selector: 'app-test-item',
    templateUrl: './test-item.component.html',
    styleUrls: ['./test-item.component.css']
})
export class TestItemComponent implements OnInit {
    @Input() test: Test;
    @Output() stateChange = new EventEmitter<void>();

    constructor(private testService: TestService) {
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

    changeState(newState: string): void {
        this.test.setState(newState);
        this.test.setLastExecution(new Date().toISOString().slice(0, 10));

        this.testService.update(this.test.getProjectId(), this.test).subscribe(
            test => {
                this.test = test;
                this.stateChange.emit();
            }
        );

    }

    onDelete(): void {
        console.log(`Delete test with id ${this.test.getId()} for project ${this.test.getProjectId()}`);
        this.testService.delete(this.test.getProjectId(), this.test);
    }
}
