import { Component, Input, OnInit } from '@angular/core';
import { Release } from 'src/app/models/release.model';

@Component({
    selector: 'app-release-item',
    templateUrl: './release-item.component.html',
    styleUrls: ['./release-item.component.css']
})
export class ReleaseItemComponent implements OnInit {

    @Input() releaseItem: Release;
    constructor() { }

    ngOnInit(): void {
    }

}
