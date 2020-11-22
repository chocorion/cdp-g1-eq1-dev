import {Component, OnInit} from '@angular/core';
import {SidebarService} from './services/sidebar.service';
import {SidebarState} from './models/sidebar-state';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
    sideBarOpen = false;

    constructor(private sidebarService: SidebarService) {
    }

    ngOnInit(): void {
        this.sidebarService.sideBarSubject.subscribe(
            next => {
                this.sideBarOpen = (next === SidebarState.open);
            }
        );
    }
}

