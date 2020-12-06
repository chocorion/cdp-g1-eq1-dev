import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Release } from 'src/app/models/release.model';
import { ProjectService } from 'src/app/services/project.service';
import { ReleaseService } from 'src/app/services/release.service';

@Component({
    selector: 'app-releases',
    templateUrl: './releases.component.html',
    styleUrls: ['./releases.component.css']
})
export class ReleasesComponent implements OnInit {
    releases: Release[];
    releaseSubscription: Subscription;
    constructor(private releaseService: ReleaseService,
                private projectService: ProjectService)
                { }

    ngOnInit(): void {
        console.log('Now entering releases component');
        this.releaseService.getAllForProject(this.projectService.currentProject.getId());
        this.releaseSubscription = this.releaseService.subject.subscribe(
            result => {
                this.releases = result;
                console.log(this.releases);

            }
        );
    }

}
