import { Component, OnInit,Input } from '@angular/core';

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.css']
})
export class ProjectListComponent implements OnInit {
  @Input() search: string;

  projects = [
    {
      id : 1,
      name: 'Projet 1',
      description: ' C\'est un projet sans grand intérêt',
      nbUS: 3,
      totalUS: 4,
      all: 4,
      done: 2

    },
    {
      id: 2,
      name: 'Projet 2',
      description: 'Un second projet sans intérêt',
      nbUS: 5,
      totalUS: 15,
      all: 10,
      done: 2
    },
    {
      id:3,
      name: 'Projet 3',
      description: 'Un troisième projet ... et devinez quoi il est aussi sans intérêt',
      nbUS: 3,
      totalUS: 4,
      all: 4,
      done: 3
    }
  ];

  constructor() { }

  ngOnInit(): void {
  }

}
