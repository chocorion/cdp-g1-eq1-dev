import { Component, OnInit } from '@angular/core';
import { SidebarState } from 'src/app/models/sidebar-state';
import { SidebarService } from 'src/app/services/sidebar.service';

@Component({
  selector: 'app-how-to',
  templateUrl: './how-to.component.html',
  styleUrls: ['./how-to.component.css']
})
export class HowToComponent implements OnInit {

  constructor(private sidebarService: SidebarService) { }

  ngOnInit(): void {
    this.sidebarService.changeState(SidebarState.disabled);
  }

}
