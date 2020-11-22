import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';
import {Router} from '@angular/router';
import {SidebarState} from '../models/sidebar-state';

@Injectable({
    providedIn: 'root'
})
export class SidebarService {
    private state = SidebarState.disabled;

    public sideBarSubject = new Subject<SidebarState>();

    constructor(private router: Router) {
        router.events.subscribe(
            () => {
                if (this.router.url === '/project') {
                    this.changeState(SidebarState.disabled);
                } else {
                    this.changeState(SidebarState.closed);
                }
            }
        );
    }

    changeState(newState: SidebarState): void {
        this.state = newState;
        this.emit();
    }

    emit(): void {
        this.sideBarSubject.next(this.state);
    }
}
