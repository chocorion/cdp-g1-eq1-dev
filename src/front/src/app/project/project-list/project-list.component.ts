import {Component, OnInit, Input, SimpleChange, SimpleChanges} from '@angular/core';
import {Project} from '../../model/project.model';
import {ProjectService} from '../../services/project.service';
import {Router} from '@angular/router';

@Component({
    selector: 'app-project-list',
    templateUrl: './project-list.component.html',
    styleUrls: ['./project-list.component.css']
})
export class ProjectListComponent implements OnInit {
    
    projects: Project[];

    constructor(
        private projectService: ProjectService,
        protected router: Router
        ) {
    }

    ngOnInit(): void {
        this.projectService.getProjects().subscribe(
                result => {
                    this.projects = result.map(x => Project.fromJSON(x));
            }
        );
    }

    notifyMe(value: string){
        this.projects = this.projects.filter(p => p.getName().includes(value));
    }

 
    onClick(project: Project): void {
        this.projectService.setCurrentProject(project);
        this.router.navigate(['tests']);
    }
}
