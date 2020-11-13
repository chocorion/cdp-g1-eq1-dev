import {Component, Input, OnInit} from '@angular/core';
import {Test} from '../../model/test.model';
import {TestService} from '../../services/test.service';

@Component({
    selector: 'app-test-item',
    templateUrl: './test-item.component.html',
    styleUrls: ['./test-item.component.css']
})
export class TestItemComponent implements OnInit {
    @Input() test: Test;

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
        // this.test.setLastExecution(new Date());
        this.testService.updateTest(this.test).subscribe(
            test => this.test = Test.fromJSON(test)
        );
    }
}
