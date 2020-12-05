import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Sprint} from '../models/sprint.model';
import {environment} from '../../environments/environment';
import {GenericService} from './genericService';
import {Us} from '../models/us.model';
import {ProjectService} from './project.service';
import {catchError} from 'rxjs/operators';
import { Release } from '../models/release.model';

@Injectable({
    providedIn: 'root'
})
export class ReleaseService extends GenericService<Release> {

    constructor(
        private http: HttpClient,
        private projectService: ProjectService
    ) {
        super(http, 'releases');
    }

    getElementFromJSON(jsonObject): Release {
        return Release.fromJSON(jsonObject);
    }


}
