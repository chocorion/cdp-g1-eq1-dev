import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

@Component({
    selector: 'app-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
    collapsed = false;
    visible = false;

    constructor(private router: Router) {
        router.events.subscribe(
            () => this.visible = this.router.url === '/project'
        );
    }

    ngOnInit(): void {

    }

    collapse(): void {
        this.collapsed = !this.collapsed;
    }
}
