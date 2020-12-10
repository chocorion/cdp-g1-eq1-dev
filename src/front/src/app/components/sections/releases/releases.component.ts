import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Release } from 'src/app/models/release.model';
import {Project} from '../../../models/project.model';
import { ProjectService } from 'src/app/services/project.service';
import { ReleaseService } from 'src/app/services/release.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-releases',
    templateUrl: './releases.component.html',
    styleUrls: ['./releases.component.css']
})
export class ReleasesComponent implements OnInit {
    releases: Release[];
    releaseSubscription: Subscription;
    constructor(private releaseService: ReleaseService,
                private projectService: ProjectService,
                private router: Router,
                private route: ActivatedRoute)
                { }

    ngOnInit(): void {
        console.log('Now entering releases component');
        this.route.paramMap.subscribe(params => {
            this.projectService.getProject(parseInt(params.get('projectId'), 10)).subscribe(
                project => {
                    this.projectService.setCurrentProject(Project.fromJSON(project));

                    this.releaseService.getAllForProject(this.projectService.currentProject.getId());
                    this.releaseSubscription = this.releaseService.getSubject(this.projectService.currentProject.getId()).subscribe(
                        result => {
                            this.releases = result;
                            console.log(this.releases);
                        }
                    );
                },
                error => this.router.navigate(['/projects'])
            );
        });
    }

}
