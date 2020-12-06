import { Component, OnInit } from '@angular/core';
import { Release } from 'src/app/models/release.model';

@Component({
    selector: 'app-releases',
    templateUrl: './releases.component.html',
    styleUrls: ['./releases.component.css']
})
export class ReleasesComponent implements OnInit {
    releases: Release[];
    constructor() { }

    ngOnInit(): void {
    }

}
