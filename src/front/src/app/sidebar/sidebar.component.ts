import { Component, OnInit } from '@angular/core';
import { element } from 'protractor';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  private visible = true;
  constructor() { }

  ngOnInit(): void {
  }

  collapse(): void{
    const sidebar = document.getElementById('sidebar');
    if (this.visible){
      sidebar.style.width = '100px';
    }
    else{
      sidebar.style.width = '250px';
    }
    this.visible = !this.visible;
    // Il faut cacher le texte
  }
}
