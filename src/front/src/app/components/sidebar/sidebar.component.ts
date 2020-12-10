import {Component, OnInit} from '@angular/core';
import {SidebarService} from '../../services/sidebar.service';
import {SidebarState} from '../../models/sidebar-state';
import {ProjectService} from 'src/app/services/project.service';


@Component({
    selector: 'app-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
    public state: SidebarState;

    public visible: boolean;
    public collapsed: boolean;

    constructor(
        private sidebarService: SidebarService,
        public projectService: ProjectService
    ) {
    }

    ngOnInit(): void {
        this.sidebarService.sideBarSubject.subscribe(
            next => {
                this.state = next;
                this.update();
            }
        );

        this.sidebarService.emit();
    }

    collapse(): void {
        if (this.state === SidebarState.closed) {
            this.sidebarService.changeState(SidebarState.open);
        } else {
            this.sidebarService.changeState(SidebarState.closed);
        }
    }

    private update(): void {
        this.visible = (this.state !== SidebarState.disabled);
        this.collapsed = (this.state === SidebarState.closed);
    }
}
